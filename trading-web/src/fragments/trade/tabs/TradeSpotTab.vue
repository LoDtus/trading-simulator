<template>
    <div class="w-full h-full px-1 !pb-1 text-white">
        <div class="flex">
            <div class="basis-[75%] mr-1">
                <SpotHeaderSide/>
                <div class="w-full flex">
                    <OrderSide/>
                    <div class="grow w-full !ml-1 flex flex-col">
                        <ChartSide/>
                        <TradingModeSide/>
                    </div>
                </div>
            </div>

            <div class="basis-[25%] flex flex-col">
                <MarketPanelSide/>
                <TradesPanelSide/>
                <TopMoversSide/>
            </div>
        </div>

        <OrdersPanelSide/>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import OrderSide from '../components/OrderSide.vue';
import ChartSide from '../components/ChartSide.vue';
import TradingModeSide from '../components/TradingModeSide.vue';
import OrdersPanelSide from '../components/OrdersPanelSide.vue';
import SpotHeaderSide from '../components/SpotHeaderSide.vue';
import TopMoversSide from '../components/TopMoversSide.vue';
import TradesPanelSide from '../components/TradesPanelSide.vue';
import MarketPanelSide from '../components/MarketPanelSide.vue';

const arr = [1,1,1,1,1,1,1,1,1];
</script>