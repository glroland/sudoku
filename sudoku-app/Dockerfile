#FROM registry.access.redhat.com/ubi8/nodejs-14:latest as build
FROM registry.access.redhat.com/ubi9/nodejs-20:1-54 as build
#FROM node:latest as build
# ENV REACT_APP_SUDOKU_URL_SVC http://c:8080
RUN mkdir /tmp/build
WORKDIR /tmp/build
RUN git clone https://github.com/glroland/sudoku.git
WORKDIR /tmp/build/sudoku/sudoku-app
ENV NODE_OPTIONS --openssl-legacy-provider
RUN npm install
RUN npm run build --openssl-legacy-provider

FROM registry.access.redhat.com/ubi9/nginx-122:1-79
EXPOSE 8080
# ENV REACT_APP_SUDOKU_URL_SVC http://a:8080
# ENV REACT_APP_SUDOKU_URL_OCRSVC http://o:3000
# ENV NODE_TLS_REJECT_UNAUTHORIZED=0
# ENV PORT=8080
RUN rm -f config.json
RUN chmod 777 /opt/app-root/src
ENV NODE_OPTIONS --openssl-legacy-provider
COPY src/config.json.env /opt/app-root/src/
COPY nginx.conf.env /opt/app-root/src/
COPY --from=build /tmp/build/sudoku/sudoku-app/container_run.sh /
COPY --from=build /tmp/build/sudoku/sudoku-app/build/ /opt/app-root/src/

CMD /bin/bash -c /container_run.sh
