const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
    // 其他配置项，如下面示例中的 transpileDependencies
    transpileDependencies: true,

    // 开发服务器相关配置放在 devServer 中
    devServer: {
        // 开发服务器的端口设置等其他配置，这里省略具体示例
        port: 80,

        // 代理配置放在 devServer 的 proxy 属性中
        proxy: {
            '/api': {
                target: 'http://192.168.66.188:8080',
                changeTarget: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
});