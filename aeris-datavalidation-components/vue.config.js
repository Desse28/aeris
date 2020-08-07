module.exports = {
  runtimeCompiler: true,
  pages: {
    index: {
      entry: './src/demo/main.js',
      template: 'public/index.html',
      title: 'validation tool',
    },
  },
  "transpileDependencies": [
    "vuetify"
  ]
}