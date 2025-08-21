<template>
    <div class="mb-1 p-2 flex items-center justify-between bg-dark-area rounded-sm">
        <div class="p-[2px] flex flex-col">
            <FontAwesomeIcon :icon="faStar" />
        </div>
        <div class="p-[2px] flex flex-col">
            <img
                class="p-[2px] w-[30px] h-[30px] aspect-square object-cover"
                src="https://i.pinimg.com/1200x/fc/5d/5d/fc5d5d6cfc2bbf01a44dff28090e9fc0.jpg"
                alt=""
            />
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="text-sm font-semibold">BTC/USDT</span>
            <span class="text-[12px] text-soft-gray">Giá Bitcoin</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span
                :class="[
                    'text-sm font-semibold',
                    'text-candlestick-green',
                ]"
            >
                115.555,66
            </span>
            <span class="text-[12px]">$115,222,22</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">Biến động trong 24h</span>
            <span
                :class="[
                    'text-[12px]',
                    'text-candlestick-red'
                ]"
            >
                -1.500,55<span class="ml-2">-2.22%</span>
            </span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">Giá cao nhất 24h</span>
            <span class="!text-[12px]">115,555.22</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">Giá cao nhất 24h</span>
            <span class="!text-[12px]">110,555.22</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">KL 24h(BTC)</span>
            <span class="!text-[12px]">18.172,91</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">KL 24h(USDT)</span>
            <span class="!text-[12px]">2.069.575.201,93</span>
        </div>
        <div class="p-[2px] flex flex-col">
            <span class="!text-[10px] text-soft-gray">Thẻ token</span>
            <span class="!text-[12px]">POW Payments Khối lượng Phổ biến Price Protection</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faStar } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';


</script>