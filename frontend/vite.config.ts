import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig(({mode}) => {
    // const env = loadEnv(mode, process.cwd(), '')
    const isProduction = mode === 'production'
    return {
        plugins: [vue()],
        server: !isProduction ? {
            proxy: {
                '/api': {
                    target: 'http://localhost:8000',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''),
                }
            }
        } : {},
        build:{},
        define: {
            __APP_VERSION__: JSON.stringify('0.0.1'),
        },
    }
});
