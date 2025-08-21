<template>
    <div class="shrink-0 bg-dark-area rounded-md ml-2">
        <ul class="h-full p-2 flex flex-col">
            <li
                v-for="(tab, tabIndex) in navigationTabs"
                :key="tabIndex"
                :class="[
                    'py-2 px-5 mb-2 flex rounded-sm text-white font-semibold cursor-pointer duration-200 active:scale-90',
                    isActiveTab(route.path, tab.path) ? 'bg-blue-500' : 'hover:bg-gray-hover'
                ]"
                @click="navigateTab(tab.path)"
            >
                <FontAwesomeIcon
                    :icon="tab?.icon"
                    class="mr-2"
                />
                {{ tab?.label }}
            </li>

            <div class="grow mb-2 border-b border-gray-line"></div>
            <li
                class="py-3 px-8 flex justify-center items-center rounded-sm text-white
                cursor-pointer duration-200 hover:bg-gray-hover active:scale-90"
                @click="router.push('/profile/123')"
            >
                Hồ sơ
            </li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { navigationTabs } from '../../configs/constant';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const navigateTab = (path: string) => {
    router.push(path);
};

const isActiveTab = (path: string, tab: string) => {
    if (tab === "/") {
        return path === "/";
    }
    return path.startsWith(tab);
};

</script>