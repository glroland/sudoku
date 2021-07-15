const { createProxyMiddleware } = require('http-proxy-middleware');
const AppConfig = require("./AppConfig.js");

module.exports = function(app) {

  app.use(
    '/api/game',
    createProxyMiddleware({
        target: (new AppConfig()).getSudokuServiceUrl(),
        changeOrigin: true,
        logLevel: "debug",
        pathRewrite: {
          '^/api/game': '', // rewrite path
        },
      })
  );

  app.use(
    '/api/ocr',
    createProxyMiddleware({
        target: (new AppConfig()).getSudokuOcrServiceUrl(),
        changeOrigin: true,
        logLevel: "debug",
        pathRewrite: {
          '^/api/ocr': '', // rewrite path
        },
      })
  );

  const health = require('@cloudnative/health-connect');
  let healthcheck = new health.HealthChecker();
  app.use('/live', health.LivenessEndpoint(healthcheck));
  app.use('/ready', health.ReadinessEndpoint(healthcheck));
  app.use('/health', health.HealthEndpoint(healthcheck));
};
