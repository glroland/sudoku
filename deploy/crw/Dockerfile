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

ENV PATH="/opt/conda/bin:/usr/bin:/usr/sbin${PATH:+:${PATH}}" \
    MANPATH="/usr/share/man:${MANPATH:-}" \
    CPATH="/usr/include${CPATH:+:${CPATH}}" \
    LD_LIBRARY_PATH="/usr/lib64${LD_LIBRARY_PATH:+:${LD_LIBRARY_PATH}}" \
    PKG_CONFIG_PATH="/usr/lib64/pkgconfig${PKG_CONFIG_PATH:+:${PKG_CONFIG_PATH}}" \
    XDG_DATA_DIRS=/usr/share:${XDG_DATA_DIRS:-/usr/local/share:/usr/share} \
    HOME=/home/jboss

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

RUN echo 'export PATH=/opt/conda/bin:$PATH' > /etc/profile.d/conda.sh && \
    curl https://repo.anaconda.com/archive/Anaconda3-2020.07-Linux-x86_64.sh -o /projects/miniconda.sh && \
    /bin/bash /projects/miniconda.sh -f -b -p /opt/conda && \
    rm /projects/miniconda.sh

EXPOSE 8080

# ENV HOME=/home/jboss \
#     NODEJS_VERSION=10 \
#     MAVEN_VERSION="3.6.3" \
#     JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk \
#     PATH=$HOME/node_modules/.bin/:$HOME/.npm-global/bin/:/opt/app-root/src/.npm-global/bin/:/usr/lib/jvm/java-1.8.0-openjdk:/opt/apache-maven/bin:/usr/bin:$PATH \
#     MANPATH="/usr/share/man:${MANPATH}" \
#     JAVACONFDIRS="/etc/java${JAVACONFDIRS:+:}${JAVACONFDIRS:-}" \
#     XDG_CONFIG_DIRS="/etc/xdg:${XDG_CONFIG_DIRS:-/etc/xdg}" \
#     XDG_DATA_DIRS="/usr/share:${XDG_DATA_DIRS:-/usr/local/share:/usr/share}" \
#     M2_HOME="/opt/apache-maven" 

# # built in Brew, use get-sources-jenkins.sh to pull latest
# COPY . /tmp/assets/

# RUN microdnf install -y bash tar gzip which shadow-utils findutils wget curl openshift-clients-4.4.0-202005231254.git.1.1e3e6b3.el8 \
#     java-1.8.0-$([[ $(uname -m) == x86_64 ]] && echo openjdk || echo openj9) \
#     java-1.8.0-$([[ $(uname -m) == x86_64 ]] && echo openjdk || echo openj9)-devel \
#     java-1.8.0-$([[ $(uname -m) == x86_64 ]] && echo openjdk || echo openj9)-headless \        
#     # CRW-919 java8/mvn + python/pip + node/npm: combine forces and make one all-in-one useful image
#     nodejs npm python36 python3-six python3-pip platform-python-pip \
#     sudo git procps-ng nss_wrapper bzip2 && \
#     microdnf update -y zlib gnutls systemd-libs systemd pango libnghttp2 sqlite libarchive && \
#     microdnf clean all && rm -rf /var/cache/yum && \
#     # TODO: why do we need this jboss user?
#     useradd -u 1000 -G wheel,root -d ${HOME} --shell /bin/bash -m jboss && \
#     mkdir -p ${HOME}/che /projects && \
#     for f in "${HOME}" "/etc/passwd" "/etc/group" "/projects"; do \
#         chgrp -R 0 ${f} && \
#         chmod -R g+rwX ${f}; \
#     done && \
#     # Generate passwd.template \
#     cat /etc/passwd | \
#     sed s#jboss:x.*#jboss:x:\${USER_ID}:\${GROUP_ID}::\${HOME}:/bin/bash#g \
#     > ${HOME}/passwd.template && \
#     # Generate group.template \
#     cat /etc/group | \
#     sed s#root:x:0:#root:x:0:0,\${USER_ID}:#g \
#     > ${HOME}/group.template && \
#     echo "jboss	ALL=(ALL)	NOPASSWD: ALL" >> /etc/sudoers && \
#     \
#     # maven stuff \
#     mkdir -p /opt; tar xzf /tmp/assets/maven.tgz -C /opt/ && \
#     # fix permissions in bin/* files \
#     for d in $(find /opt/apache-maven -name bin -type d); do echo $d; chmod +x $d/*; done && \
#     \
#     # node stuff \
#     mkdir -p ${HOME}/lang-server /opt/app-root/src/.npm-global/bin && \
#     ln -s /usr/bin/node /usr/bin/nodejs && \
#     if [[ -f /tmp/assets/codeready-workspaces-stacks-language-servers-dependencies-node10-$(uname -m).tar.gz ]]; then \
#         tar xzf /tmp/assets/codeready-workspaces-stacks-language-servers-dependencies-node10-$(uname -m).tar.gz -C ${HOME}/lang-server/ && \
#         ln -s ${HOME}/lang-server/node_modules/typescript-language-server/lib/cli.js /opt/app-root/src/.npm-global/bin/typescript-language-server && \
#         ln -s ${HOME}/lang-server/node_modules/nodemon/bin/nodemon.js /opt/app-root/src/.npm-global/bin/nodemon && \
#         ln -s ${HOME}/lang-server/node_modules/typescript/bin/tsc /opt/app-root/src/.npm-global/bin/tsc && \
#         ln -s ${HOME}/lang-server/node_modules/typescript/bin/tsserver /opt/app-root/src/.npm-global/bin/tsserver && \
#         for f in "/opt/app-root/src/.npm-global"; do chgrp -R 0 ${f}; chmod -R g+rwX ${f}; done; \
#     else \
#         echo "[WARNING] Node lang server dependency tarball not found. Node support may be more limited on $(uname -m)"; \
#     fi
# RUN \
#     # python stuff
#     ln -s /usr/bin/python3.6 /usr/bin/python && \
#     ln -s /usr/bin/pip3.6 /usr/bin/pip && \
#     if [[ -f /tmp/assets/codeready-workspaces-stacks-language-servers-dependencies-python-$(uname -m).tar.gz ]]; then \
#         mkdir -p /tmp/py-unpack /opt/app-root/bin /opt/app-root/lib /opt/app-root/lib64 && \
#         tar -xf /tmp/assets/codeready-workspaces-stacks-language-servers-dependencies-python-$(uname -m).tar.gz -C /tmp/py-unpack && \
#         cp -R /tmp/py-unpack/bin/* /opt/app-root/bin && \
#         cp -R /tmp/py-unpack/lib/* /opt/app-root/lib && \
#         cp -R /tmp/py-unpack/lib64/* /opt/app-root/lib64 && \
#         for f in /opt/app-root/; do chgrp -R 0 ${f}; chmod -R g+rwX ${f}; done; \
#     else \
#         echo "[WARNING] Python lang server dependency tarball not found. Python support may be more limited on $(uname -m)"; \
#     fi
# RUN \
#     # cleanup and summaries
#     rm -fr /tmp/assets/ && \
#     echo "Installed Packages" && rpm -qa | sort -V && echo "End Of Installed Packages" && \
#     echo "========" && \
#     java -version && \
#     mvn -version && \
#     echo "========" && \
#     echo -n "Node "; node --version && \
#     echo -n "npm ";  npm --version && \
#     echo "========" && \
#     python -V && \
#     pip -V && \
#     echo "========"

# ADD entrypoint.sh ${HOME}/
# RUN chmod +x ${HOME}/*.sh





USER jboss
ENTRYPOINT ["/home/jboss/entrypoint.sh"]
WORKDIR /projects
CMD tail -f /dev/null
