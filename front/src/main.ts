import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// ElementPlust 사용 임포트
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// bootstrap 사용 임포트
import "bootstrap/dist/css/bootstrap-utilities.rtl.css"

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus) // ElementPlus 추가

app.mount('#app')
