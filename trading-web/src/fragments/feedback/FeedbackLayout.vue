<!-- giới thiệu (cảm ơn), lựa chọn ẩn danh hoặc để lại thông tin nếu chưa đăng nhập, 
báo lỗi, đề xuất tính năng, góp ý cải thiện, khác,
đính kèm ảnh
-->
<template>
    <div class="p-5 text-white">
        <h2 class="!font-semibold text-2xl">Góp ý cải thiện</h2>

        <div class="flex">
            <div>
                <div class="grid grid-cols-2 items-center gap-y-2">
                    <span>Giao diện</span>
                    <a-rate
                        class=""
                        v-model:value="myRate"
                        allow-half
                    />
                    <span>Chức năng</span>
                    <a-rate
                        class=""
                        v-model:value="myRate"
                        allow-half
                    />
                    <span>Giá trị thực tiễn</span>
                    <a-rate
                        class=""
                        v-model:value="myRate"
                        allow-half
                    />
                    <span>Hiệu năng</span>
                    <a-rate
                        class=""
                        v-model:value="myRate"
                        allow-half
                    />
                    <span>Mức độ hài lòng</span>
                    <a-rate
                        class=""
                        v-model:value="myRate"
                        allow-half
                    />
                </div>
            </div>

            <div>
                <span>Bạn biết đến sản phẩm qua đâu?</span>
                
            </div>
        </div>

        <a-textarea
            v-model:value="myFeedback"
            placeholder="Bạn có góp ý nào không?"
            :rows="5"
        />
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const myRate = ref(5);
const myFeedback = ref<string>('');
</script>