<template>
    <div class="px-5 pb-5 flex flex-col items-center">
        <span class="w-full text-left text-2xl font-semibold">Convert {{ fromCurrency }} to {{ toCurrency }}</span>
        <div class="w-full flex items-start">
            <span class="text-sm text-soft-gray">Instant Price | Guaranteed Price | Any Pair</span>
            <div class="grow"></div>
            <button
                class="!ml-2 py-2 px-3 rounded-sm duration-200 hover:bg-soft-gray-hover"
                @click=""
            >
                <FontAwesomeIcon :icon="faUpDown" />
                <span class="ml-1 font-semibold">Trade Spot</span>
            </button>
            <button
                class="!ml-1 py-2 px-3 rounded-sm duration-200 hover:bg-soft-gray-hover"
                @click=""
            >
                <FontAwesomeIcon :icon="faClockRotateLeft"/>
                <span class="ml-1 font-semibold">Trade History</span>
            </button>
        </div>

        <div class="w-full mt-2 flex justify-center">
            <div class="basis-[40%] mr-2 p-3 rounded-md border !border-gray-line bg-dark-area">
                <div class="mb-2 flex items-center !text-sm !font-semibold">
                    <button
                        v-for="(tab, tabIndex) in tabs"
                        :key="tabIndex"
                        :class="[
                            'shrink-0 !mr-5 duration-200',
                            selectedTab === tab?.id ? '!text-white' : '!text-soft-gray hover:!text-white'
                        ]"
                        @click="selectedTab = tab?.id"
                    >
                        {{ tab?.label }}
                    </button>
                </div>
                <div class="relative flex flex-col items-center justify-center">
                    <div class="w-full h-[150px] p-3 border !border-soft-gray rounded-md mb-2">
                        <span class="text-sm text-soft-gray">From</span>
                    </div>
                    <button class="absolute !text-soft-gray
                        w-[40px] h-[40px] aspect-square
                        flex justify-center items-center
                        bg-dark-area border !border-soft-gray rounded-md
                        duration-200 hover:!text-white active:scale-95"
                    >
                        <FontAwesomeIcon :icon="faUpDown"/>
                    </button>
                    <div class="w-full h-[150px] p-3 border !border-soft-gray rounded-md mt-2">
                        <span class="text-sm text-soft-gray">To</span>
                    </div>
                </div>
            </div>
            
            <div
                class="basis-[60%] py-2 px-3 bg-dark-area rounded-md border !border-gray-line"
            >
                a
            </div>
        </div>

        <div class="w-full mt-8 text-left">
            <span class="block font-semibold text-xl">Convert {{ fromCurrency }} to other currencies</span>
            <span class="block text-sm text-soft-gray">Swap your Bitcoin to other currencies easily in one go!</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faClockRotateLeft, faUpDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';

const tabs = [
    { id: "instant", label: "Instant" },
    { id: "recurring", label: "Recurring" },
    { id: "limit", label: "Limit" },
];
const selectedTab = ref<string>('instant');
const fromCurrency = ref<string>('USDT');
const toCurrency = ref<string>('BTC');
</script>