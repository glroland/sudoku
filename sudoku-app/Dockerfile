FROM registry.access.redhat.com/ubi8/nodejs-14:latest as build
# ENV REACT_APP_SUDOKU_URL_SVC http://c:8080
RUN mkdir /tmp/build
WORKDIR /tmp/build
RUN git clone https://github.com/glroland/sudoku.git
WORKDIR /tmp/build/sudoku/sudoku-app
RUN npm install
RUN npm run build

FROM registry.access.redhat.com/ubi8/nginx-118:latest
EXPOSE 8080
# ENV REACT_APP_SUDOKU_URL_SVC http://a:8080
# ENV REACT_APP_SUDOKU_URL_OCRSVC http://o:3000
# ENV NODE_TLS_REJECT_UNAUTHORIZED=0
# ENV PORT=8080
RUN rm -f config.json
RUN chmod 777 /opt/app-root/src
RUN ln -s /tmp/nginx.conf /etc/nginx/conf.d/nginx.conf
COPY src/config.json.env /opt/app-root/src/
COPY nginx.conf.env /opt/app-root/src/
COPY --from=build /tmp/build/sudoku/sudoku-app/container_run.sh /
COPY --from=build /tmp/build/sudoku/sudoku-app/build/ /opt/app-root/src/

CMD /bin/bash -c /container_run.sh
