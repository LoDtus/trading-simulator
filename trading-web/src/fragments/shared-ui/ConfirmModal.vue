<template>
    <Teleport to="body">
        <div
            v-if="visible"
            class="fixed inset-0 flex items-center justify-center"
        >
            <div
                class="absolute top-0 left-0 w-full h-full bg-black/50"
                @click="onConfirm"
            ></div>
            <div class="z-1 bg-white p-5 rounded-lg shadow-lg w-96">
                <h2 class="!text-xl !font-semibold mb-4">
                    {{ title }}
                </h2>
                <p class="!mt-1 !mb-6">{{ message }}</p>
                <div class="flex justify-end gap-2 !font-semibold">
                    <button
                        class="px-5 py-2 rounded-sm bg-gray-200 duration-200 hover:bg-gray-300 active:scale-90"
                        @click="onCancel"
                    >
                        Hủy
                    </button>
                    <button
                        class="px-5 py-2 rounded-sm !text-white duration-200 active:scale-90"
                        :class="confirmButtonClass"
                        @click="onConfirm"
                    >
                        {{ confirmText }}
                    </button>
                </div>
            </div>
        </div>
    </Teleport>
</template>

<script setup>
import { ref } from "vue";

const visible = ref(false);
const title = ref("");
const message = ref("");
const confirmText = ref("Đồng ý");
const confirmButtonClass = ref("bg-blue-500 hover:bg-blue-600");

let resolveFn;

function open(options) {
    title.value = options.title || "Xác nhận";
    message.value = options.message || "Bạn có muốn tiếp tục thực hiện không?";
    confirmText.value = options.confirmText || "Đồng ý";
    confirmButtonClass.value = options.confirmButtonClass || "bg-blue-500 hover:bg-blue-600";
    visible.value = true;

    return new Promise((resolve) => {
        resolveFn = resolve;
    });
}

function onConfirm() {
    visible.value = false;
    resolveFn(true);
}
function onCancel() {
    visible.value = false;
    resolveFn(false);
}

defineExpose({ open });
</script>
