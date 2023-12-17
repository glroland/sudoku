FROM continuumio/miniconda3:23.10.0-1

LABEL name="odh-notebook-jupyter-minimal-ubi9-python-3.9" \
    summary="Minimal Jupyter notebook image for ODH notebooks" \
    description="Minimal Jupyter notebook image with base Python 3.9 builder image based on UBI9 for ODH notebooks" \
    io.k8s.display-name="Minimal Jupyter notebook image for ODH notebooks" \
    io.k8s.description="Minimal Jupyter notebook image with base Python 3.9 builder image based on UBI9 for ODH notebooks" \
    authoritative-source-url="https://github.com/opendatahub-io/notebooks" 

RUN mkdir -p /opt/app-root/bin
WORKDIR /opt/app-root/bin

COPY environment.yml start-notebook.sh ./

RUN echo "export CONDA_ENV_NAME=$(head -1 environment.yml | cut -d' ' -f2)" >> ~/.bashrc

# - Create a Python virtual environment for use by any application to avoid
#   potential conflicts with Python packages preinstalled in the main Python
#   installation.
# - In order to drop the root user, we have to make some directories world
#   writable as OpenShift default security model is to run the container
#   under random UID.
RUN /opt/conda/bin/conda env create --file environment.yml && \
    rm environment.yml

# For RHEL/Centos 8+ scl_enable isn't sourced automatically in s2i-core
# so virtualenv needs to be activated this way
ENV BASH_ENV="source /opt/conda/bin/activate /opt/conda/envs/sudoku-ai" \
    ENV="source /opt/conda/bin/activate /opt/conda/envs/sudoku-ai" \
    PROMPT_COMMAND=". /opt/conda/bin/activate /opt/conda/envs/sudoku-ai"

# Disable announcement plugin of jupyterlab
RUN /opt/conda/envs/sudoku-ai/bin/jupyter labextension disable "@jupyterlab/apputils-extension:announcements"

# Fix permissions to support pip in Openshift environments
#RUN chmod -R g+w /opt/conda/envs/sudoku-ai
RUN chmod -R g+w /opt/app-root/bin

# Replace Notebook's launcher, "(ipykernel)" with Python's version 3.x.y
RUN sed -i -e "s/Python.*/$(python --version | cut -d '.' -f-2)\",/" /opt/conda/envs/sudoku-ai/share/jupyter/kernels/python3/kernel.json

USER 1001

CMD ["/bin/bash", "-c", "source /opt/conda/bin/activate /opt/conda/envs/sudoku-ai && ./start-notebook.sh"]

