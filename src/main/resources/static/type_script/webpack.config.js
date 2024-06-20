const path = require('path');

module.exports = {
    mode: "production",
    entry: "./App.ts",
    output: {
        filename: "webapp.js",
        path: path.resolve(__dirname, "../")
    },
    resolve: {
        extensions: [".ts", ".js"]
    },
    module: {
        rules: [
            {
                test: /\.ts/,
                use: "ts-loader",
                exclude: /node_modules/
            }
        ]
    }
}