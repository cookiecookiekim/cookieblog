import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  // 프론트에서 cors 에러 해결하는 방법
  server: {
    proxy : {
      "/api" : {
        target : "http://localhost:8080",
        rewrite: (path) => path.replace(/^\/api/,"")} // api 시작 부분을 공백으로 날려주겠다
    }
  }
})
