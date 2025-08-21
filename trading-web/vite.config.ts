import { defineConfig, loadEnv } from "vite";
import tailwindcss from '@tailwindcss/vite';
import vue from "@vitejs/plugin-vue";
import path from 'path';

export default defineConfig(({ mode }) => {
    const env = loadEnv(mode, process.cwd());

    return {
        plugins: [
            vue(),
            tailwindcss(),
        ],
        build: {
            sourcemap: true,
        },
        server: {
            port: parseInt(env.VITE_PORT || "5173", 10),
            host: '0.0.0.0'
        }
    }
});
