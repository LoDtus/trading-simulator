import { createVNode, render } from "vue";
import ConfirmModal from "../fragments/shared-ui/ConfirmModal.vue";

let instance: any;

// Tạo modal xác nhận chung khi được gọi
export function useConfirm() {
    if (!instance) {
        const container = document.createElement("div");
        document.body.appendChild(container);
        const vnode = createVNode(ConfirmModal);
        render(vnode, container);

        if (!vnode.component) {
            throw new Error("Failed to mount ConfirmModal");
        }

        instance = vnode.component.exposed as {
            open: (options: any) => Promise<boolean>;
        };
    }

    return (options = {}) => {
        return instance.open(options);
    };
}
