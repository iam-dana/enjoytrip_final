import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // 빌드 설정
  build: {
    // sourcemap: false,
    // outDir: 'C:/SSAFY/01_workspace/09_Spring/pair09_KangMinJung_KimDaNa/pair09_kangminjung_kimdana/src/main/resources/static', // 민정싸피
    outDir: 'C:/pair09_KangMinJung_KimDaNa/pair09_kangminjung_kimdana/src/main/resources/static' // 민정노트북
    // 다나 맥북
    // outDir: '/Users/dana/git/pair/pair09_kangminjung_kimdana/src/main/resources/static'
  }
})
