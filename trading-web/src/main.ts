import 'ant-design-vue/dist/reset.css';
import './style.css';
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import Antd from 'ant-design-vue';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue';
import appRouter from './routers';

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App);

app.use(pinia);
app.use(appRouter);
app.use(Antd);

app.mount('#app');