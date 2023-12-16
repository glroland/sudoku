ARG BASE_IMAGE
FROM ${BASE_IMAGE}

LABEL name="odh-notebook-jupyter-minimal-ubi9-python-3.9" \
    summary="Minimal Jupyter notebook image for ODH notebooks" \
    description="Minimal Jupyter notebook image with base Python 3.9 builder image based on UBI9 for ODH notebooks" \
    io.k8s.display-name="Minimal Jupyter notebook image for ODH notebooks" \
    io.k8s.description="Minimal Jupyter notebook image with base Python 3.9 builder image based on UBI9 for ODH notebooks" \
    authoritative-source-url="https://github.com/opendatahub-io/notebooks" \
    io.openshift.build.commit.ref="main" \
    io.openshift.build.source-location="https://github.com/opendatahub-io/notebooks/tree/main/jupyter/minimal/ubi9-python-3.9" \
    io.openshift.build.image="quay.io/opendatahub/workbench-images:jupyter-minimal-ubi9-python-3.9"

WORKDIR /opt/app-root/bin

COPY ../environment.yml start-notebook.sh ./

ARG ANACONDA_INSTALLER_NAME=Miniconda3-latest-Linux-x86_64.sh

RUN INSTALL_PKGS="nss_wrapper \
    httpd httpd-devel mod_ssl mod_auth_gssapi mod_ldap \
    mod_session atlas-devel gcc-gfortran libffi-devel libtool-ltdl enchant" && \
    curl -O https://repo.anaconda.com/miniconda/${ANACONDA_INSTALLER_NAME} && \
    chmod +x ${ANACONDA_INSTALLER_NAME} && \
    ./${ANACONDA_INSTALLER_NAME} -b -p /opt/anaconda3 && \
    rm ./${ANACONDA_INSTALLER_NAME} && \
    /opt/anaconda3/bin/conda init && \
    yum -y module disable python38:3.8 && \
    yum -y module enable httpd:2.4 && \
    yum -y --setopt=tsflags=nodocs update && \
    yum -y --setopt=tsflags=nodocs install $INSTALL_PKGS && \
    rpm -V $INSTALL_PKGS && \
    # Remove redhat-logos-httpd (httpd dependency) to keep image size smaller.
    rpm -e --nodeps redhat-logos-httpd && \
    yum -y clean all --enablerepo='*'

# Copy the S2I scripts to $STI_SCRIPTS_PATH.
COPY ./s2i/bin/ $STI_SCRIPTS_PATH

COPY environment.yml ./
# - Create a Python virtual environment for use by any application to avoid
#   potential conflicts with Python packages preinstalled in the main Python
#   installation.
# - In order to drop the root user, we have to make some directories world
#   writable as OpenShift default security model is to run the container
#   under random UID.
RUN /opt/anaconda3/bin/conda env create --file environment.yml -p "${APP_ROOT}" && \
    rm environment.yml && \
    chown -R 1001:0 ${APP_ROOT} && \
    fix-permissions ${APP_ROOT} -P && \
    fix-permissions /opt/anaconda3 -P && \
    rpm-file-permissions

# For RHEL/Centos 8+ scl_enable isn't sourced automatically in s2i-core
# so virtualenv needs to be activated this way
ENV BASH_ENV="source /opt/anaconda3/bin/activate ${APP_ROOT}" \
    ENV="source /opt/anaconda3/bin/activate ${APP_ROOT}" \
    PROMPT_COMMAND=". /opt/anaconda3/bin/activate ${APP_ROOT}"

USER 1001

# Disable announcement plugin of jupyterlab
RUN jupyter labextension disable "@jupyterlab/apputils-extension:announcements"

# Fix permissions to support pip in Openshift environments
RUN chmod -R g+w /opt/app-root/lib/python3.9/site-packages && \
      fix-permissions /opt/app-root -P

WORKDIR /opt/app-root/src

# Replace Notebook's launcher, "(ipykernel)" with Python's version 3.x.y
RUN sed -i -e "s/Python.*/$(python --version | cut -d '.' -f-2)\",/" /opt/app-root/share/jupyter/kernels/python3/kernel.json

ENTRYPOINT ["start-notebook.sh"]

