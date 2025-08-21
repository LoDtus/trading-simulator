<template>
    <div class="h-full p-5 rounded-md">
        <h2 class="!font-semibold text-white text-2xl">Bảng xếp hạng</h2>

        <div class="flex">
            <div class="basis-[40%]">
                <ul class="grid grid-cols-3 gap-2">
                    <li
                        v-for="(position, posIndex) in [rank[1], rank[0], rank[2]]"
                        :key="posIndex"
                        :class="[
                            'px-2 py-5 flex flex-col items-center rounded-md bg-white',
                            { '!mt-5': posIndex === 0,
                            '!mt-0': posIndex === 1,
                            '!mt-10': posIndex === 2 }
                        ]"
                    >
                        <img
                            class="w-[100px] h-[100px] aspect-square rounded-full object-cover"
                            src="https://i.pinimg.com/1200x/f6/ca/4e/f6ca4e472a169c12eef87d0be64a6673.jpg"
                            alt=""
                        />
                        <span>User Top {{ position }}</span>
                        <span>example@mail.com</span>
                        <span>$2.000.000</span>
                        <span>+$2.000</span>
                    </li>
                </ul>

                <div class="w-full text-white flex justify-end items-center">
                    <span>Xếp theo</span>
                    <span>Từ cao đến thấp</span>
                </div>
                
                <ul>
                    <li
                        v-for="(position, posIndex) in rank"
                        :key="posIndex"
                        class="py-2 flex items-center border"
                    >
                        <span class="basis-[10%] px-2 text-center text-white">{{ posIndex + 1 }}</span>
                        <div class="basis-[60%] flex">
                            <img
                                class="w-[40px] h-[40px] aspect-square rounded-full object-cover"
                                src="https://i.pinimg.com/1200x/b1/4d/ff/b14dffff4562e94d8ed17b7dc5a851e2.jpg"
                                alt=""
                            />
                            <div class="grow px-2">
                                <span class="text-white line-clamp-1">Lorem ipsum dolor sit amet consectetur adipisicing elit. Unde temporibus aut explicabo asperiores itaque, fuga odit reiciendis. Repellendus, sunt, quisquam voluptatem minus distinctio vel praesentium quis qui a, voluptatibus nulla? {{ posIndex }}</span>
                                <span class="text-soft-gray line-clamp-1 text-sm">example@mail.com</span>
                            </div>
                        </div>
                        <div class="basis-[20%] px-2">
                            <span class="block text-white">+$2,000.22</span>
                            <span class="block text-white">1tr</span>
                        </div>
                        <div class="basis-[10%] px-2 flex justify-center">
                            <FontAwesomeIcon :icon="faFire" color="red"/>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="basis-[60%]">
                <span class="font-semibold text-2xl text-white">Hạng của tôi</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faFire } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const rank = [1,2,3,4,5,6,7,7,8,8,9,10];
</script>