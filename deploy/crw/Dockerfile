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

ENV PATH="/opt/conda/bin:/usr/bin:/usr/sbin${PATH:+:${PATH}}" \
    MANPATH="/usr/share/man:${MANPATH:-}" \
    CPATH="/usr/include${CPATH:+:${CPATH}}" \
    LD_LIBRARY_PATH="/usr/lib64${LD_LIBRARY_PATH:+:${LD_LIBRARY_PATH}}" \
    PKG_CONFIG_PATH="/usr/lib64/pkgconfig${PKG_CONFIG_PATH:+:${PKG_CONFIG_PATH}}" \
    XDG_DATA_DIRS=/usr/share:${XDG_DATA_DIRS:-/usr/local/share:/usr/share} \
    PYTHONPATH=/deploy \
    HOME=/home/jboss

USER root

RUN echo 'export PATH=/opt/conda/bin:$PATH' > /etc/profile.d/conda.sh && \
    curl https://repo.anaconda.com/archive/Anaconda3-2020.07-Linux-x86_64.sh -o ~/miniconda.sh && \
    /bin/bash ~/miniconda.sh -f -b -p /opt/conda && \
    rm ~/miniconda.sh

RUN yum install -y --setopt=tsflags=nodocs wget curl openshift-clients-4.3.19 \
    yum update -y librepo libnghttp2 dbus dbus dbus-common dbus-daemon dbus-libs dbus-tools systemd nodejs npm libicu gnutls && \
    useradd -u 1000 -G wheel,root -d ${HOME} --shell /bin/bash -m jboss && \
    mkdir -p /projects && \
    for f in "${HOME}" "/etc/passwd" "/etc/group" "/projects"; do \
      chgrp -R 0 ${f} && \
      chmod -R g+rwX ${f}; \
    done && \
    cat /etc/passwd | \
    sed s#jboss:x.*#jboss:x:\${USER_ID}:\${GROUP_ID}::\${HOME}:/bin/bash#g \
    > ${HOME}/passwd.template && \
    cat /etc/group | \
    sed s#root:x:0:#root:x:0:0,\${USER_ID}:#g \
    > ${HOME}/group.template && \
    echo "jboss ALL=(ALL)       NOPASSWD: ALL" >> /etc/sudoers && \
    echo "Installed Packages" && rpm -qa | sort -V && echo "End Of Installed Packages"

ADD entrypoint.sh ${HOME}/entrypoint.sh

EXPOSE 8080

USER jboss
ENTRYPOINT ["/home/jboss/entrypoint.sh"]
WORKDIR /projects
CMD tail -f /dev/null
