const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api/game',
    createProxyMiddleware({
        target: process.env.REACT_APP_SUDOKU_URL_SVC || 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/api/game': '', // rewrite path
        },
      })
  );

  app.use(
    '/api/ocr',
    createProxyMiddleware({
        target: process.env.REACT_APP_SUDOKU_URL_OCRSVC || 'http://localhost:5000',
        changeOrigin: true,
        pathRewrite: {
          '^/api/ocr': '', // rewrite path
        },
      })
  );
};
