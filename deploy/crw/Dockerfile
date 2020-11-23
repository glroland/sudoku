# https://access.redhat.com/containers/?tab=tags#/registry.access.redhat.com/ubi8-minimal
FROM registry.access.redhat.com/ubi8-minimal:8.2-349

ENV SUMMARY="Red Hat CodeReady Workspaces - Anaconda3 Stack container" \
    DESCRIPTION="Red Hat CodeReady Workspaces - Anaconda3 Stack container" \
    PRODNAME="codeready-workspaces" \
    COMPNAME="stacks-anaconda3-rhel8"

LABEL summary="$SUMMARY" \
      description="$DESCRIPTION" \
      io.k8s.description="$DESCRIPTION" \
      io.k8s.display-name="$DESCRIPTION" \
      io.openshift.tags="$PRODNAME,$COMPNAME" \
      com.redhat.component="$PRODNAME-$COMPNAME-container" \
      name="$PRODNAME/$COMPNAME" \
      version="1.0" \
      license="EPLv2" \
      maintainer="glroland@hotmail.com" \
      io.openshift.expose-services="" \
      usage=""

USER root

ENV HOME=/home/jboss \
    PATH=/usr/bin:$PATH \
    MANPATH="/usr/share/man:${MANPATH}" \
    XDG_CONFIG_DIRS="/etc/xdg:${XDG_CONFIG_DIRS:-/etc/xdg}" \
    XDG_DATA_DIRS="/usr/share:${XDG_DATA_DIRS:-/usr/local/share:/usr/share}"

RUN microdnf install -y bash tar gzip which shadow-utils findutils wget curl \
    sudo git procps-ng nss_wrapper bzip2 && \
    microdnf update -y zlib gnutls systemd-libs systemd pango libnghttp2 sqlite libarchive && \
    microdnf clean all && rm -rf /var/cache/yum && \
    useradd -u 1000 -G wheel,root -d ${HOME} --shell /bin/bash -m jboss && \
    mkdir -p ${HOME}/che /projects && \
    for f in "${HOME}" "/etc/passwd" "/etc/group" "/projects"; do \
        chgrp -R 0 ${f} && \
        chmod -R g+rwX ${f}; \
    done && \
    # Generate passwd.template \
    cat /etc/passwd | \
    sed s#jboss:x.*#jboss:x:\${USER_ID}:\${GROUP_ID}::\${HOME}:/bin/bash#g \
    > ${HOME}/passwd.template && \
    # Generate group.template \
    cat /etc/group | \
    sed s#root:x:0:#root:x:0:0,\${USER_ID}:#g \
    > ${HOME}/group.template && \
    echo "jboss	ALL=(ALL)	NOPASSWD: ALL" >> /etc/sudoers

RUN \
    # cleanup and summaries
    rm -fr /tmp/assets/ && \
    echo "Installed Packages" && rpm -qa | sort -V && echo "End Of Installed Packages"

ADD entrypoint.sh ${HOME}/entrypoint.sh
RUN chmod +x ${HOME}/*.sh

RUN curl https://mirror.openshift.com/pub/openshift-v4/clients/ocp/latest/openshift-client-linux.tar.gz -o openshift-client.tar.gz && \
    gzip -dc openshift-client.tar.gz | tar xvf - && \
    mv oc /usr/local/bin && \
    mv kubectl /usr/local/bin && \
    rm -f openshift-client.tar.gz

RUN echo 'export PATH=/opt/conda/bin:$PATH' > /etc/profile.d/conda.sh && \
    curl https://repo.anaconda.com/archive/Anaconda3-2020.07-Linux-x86_64.sh -o /projects/miniconda.sh && \
    /bin/bash /projects/miniconda.sh -f -b -p /opt/conda && \
    rm /projects/miniconda.sh

EXPOSE 8080


USER jboss
ENTRYPOINT ["/home/jboss/entrypoint.sh"]
WORKDIR /projects
CMD tail -f /dev/null
