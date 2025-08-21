<template>
    <div class="!w-full py-2 px-5 !flex !justify-between !items-center text-white">
        <h1
            class="shrink-0 mr-2 !font-semibold text-lg text-white
            cursor-pointer duration-200 active:scale-95"
            @click="router.push('/')"
        >
            Trading Simulator
        </h1>

        <ul class="flex">
            <li
                v-for="(tab, tabIndex) in navigationTabs"
                :key="tabIndex"
                :class="[
                    'py-1 px-2 mx-1 font-semibold text-soft-gray cursor-pointer duration-200 active:scale-95',
                    isActiveTab(route?.path, tab?.path) ? 'text-white' : 'hover:text-white'
                ]"
                @click="navigateTab(tab?.path)"
            >
                {{ tab?.label }}
            </li>
        </ul>

        <button
            v-if="!user?.id?.trim()"
            class="py-2 px-5 !font-semibold !text-black bg-white !rounded-full
            duration-200 hover:bg-gray active:scale-95"
            @click="navigateTab('/auth/sign-in')"
        >
            Đăng nhập
        </button>

        <div class="flex items-center">
            <button
                class="relative w-[33px] h-[33px] !mr-1 aspect-square rounded-full
                duration-200 hover:bg-gray-hover active:scale-95"
            >
                <div class="absolute top-0 right-0 pt-[2px] px-1 rounded-full text-[10px] bg-red-500">
                    22
                </div>
                <FontAwesomeIcon
                    class=""
                    :icon="faBell"
                />
            </button>
            <a-popover
                placement="bottomRight"
                color="white"
                trigger="click"
            >

                <button
                    class="p-1 rounded-full
                    duration-200 hover:bg-gray-hover active:scale-95"
                >
                    <img
                        class="w-[30px] h-[30px] aspect-square object-cover rounded-full"
                        src="https://i.pinimg.com/736x/3f/40/e4/3f40e4de24bbbdb15af1c52541b6371f.jpg"
                        alt="Profile"
                    />
                </button>

                <template #content>
                    <ul>
                        <li
                            v-for="(tab, tabIndex) in profileMenu"
                            :key="tabIndex"
                            :class="[
                                'py-1 px-5 border rounded-sm !border-black',
                                'cursor-pointer duration-200 active:scale-95',
                                { 'mt-1': tabIndex > 0 },
                            ]"
                            @click="navigateTab(tab?.path)"
                        >
                            {{ tab?.label }}
                        </li>
                    </ul>
                </template>
            </a-popover>
        </div>
    </div>
</template>

<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { useRoute, useRouter } from 'vue-router';
import { navigationTabs } from '../../configs/constant';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import { useUserStore } from '../../stores/userStore';

const router = useRouter();
const route = useRoute();
const user = useUserStore();

const profileMenu = [
    { id: 'profile', path: `/profile/${user?.id}`, label: 'Trang cá nhân' },
    { id: 'setting', path: '/setting', label: 'Cài đặt' },
    { id: 'support', path: '/support', label: 'Hỗ trợ' },
    { id: 'feedback', path: '/feedback', label: 'Góp ý' },
];

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