FROM registry.access.redhat.com/ubi8/nodejs-14:latest as build
ENV REACT_APP_SUDOKU_URL_SVC http://c:8080
WORKDIR /deployments
COPY * /deployments/
RUN npm install
COPY ./ /deployments/
COPY ./.npmignore /deployments/
RUN npm run build

FROM registry.access.redhat.com/ubi8/nginx-118:latest
EXPOSE 8080
# ENV REACT_APP_SUDOKU_URL_SVC http://a:8080
# ENV REACT_APP_SUDOKU_URL_OCRSVC http://o:3000
# ENV NODE_TLS_REJECT_UNAUTHORIZED=0
# ENV PORT=8080
RUN rm -f config.json
COPY src/config.json.env /opt/app-root/src/
COPY container_run.sh /
COPY --from=build /deployments/build/ /opt/app-root/src/

CMD /bin/bash -c /container_run.sh
