<template>
    <div class="mt-1 bg-dark-area rounded-sm">
        <div class="px-3 py-2 flex items-center border-b border-gray-line">
            <button
                v-for="(mode, modeIndex) in modeTabs"
                :key="modeIndex"
                :class="[
                    'shrink-0 !mr-5 !font-semibold !text-sm duration-200',
                    selectedMode === mode?.id ? '!text-white' : '!text-soft-gray hover:!text-white'
                ]"
                @click="selectedMode = mode?.id"
            >
                {{ mode?.label }}
            </button>
        </div>

        <div class="p-3">

        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const modeTabs = [
    { id: "spot", label: "Spot" },
    { id: "cross", label: "Cross" },
    { id: "isolated", label: "Isolated" },
    { id: "grid", label: "Grid" },
];
const selectedMode = ref<string>('spot');
</script>