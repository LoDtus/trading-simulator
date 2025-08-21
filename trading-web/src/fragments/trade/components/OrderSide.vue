<template>
    <div class="shrink-0 bg-dark-area rounded-sm">
        <div class="!w-full p-2 flex items-center justify-between border-b border-gray-line">
            <span class="text-sm font-semibold">Sổ lệnh</span>
            <FontAwesomeIcon
                :icon="faEllipsis"
                class="text-soft-gray cursor-pointer duration-200 hover:text-white active:scale-95"
            />
        </div>

        <div class="flex text-[12px]">
            <span class="text-left">Giá (USDT)</span>
            <span class="text-center">Số lượng (BTC)</span>
            <span class="text-right">Tổng</span>
        </div>
        <ul class="text-[12px]">
            <li v-for="(orderItem, orderIndex) in arr" :key="orderIndex" class="flex p-1">
                <span class="text-left">110.500,22</span>
                <span class="text-center">12,025</span>
                <span class="text-right">9,66K</span>
            </li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const arr = [1,1,1,1,1,1,1,1,1];
</script>