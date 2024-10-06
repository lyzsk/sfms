<template>
    <div :class="{ hidden: hidden }" class="pagination_container">
        <el-pagination
            :background="background"
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :layout="layout"
            :page-sizes="pageSizes"
            :pager-count="pagerCount"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
    </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
    total: number;
    page?: number;
    limit?: number;
    pageSizes?: number[];
    pagerCount?: number;
    layout?: string;
    background?: boolean;
    autoScroll?: boolean;
    hidden?: boolean;
}>();

const emit = defineEmits<{
    (e: "update:page", val: number): void;
    (e: "update:limit", val: number): void;
    (e: "pagination", payload: { page: number; limit: number }): void;
}>();

const currentPage = computed({
    get() {
        return props.page ?? 1;
    },
    set(val) {
        emit("update:page", val);
    },
});

const pageSize = computed({
    get() {
        return props.limit ?? 20;
    },
    set(val) {
        emit("update:limit", val);
    },
});

function handleSizeChange(val: number) {
    if (currentPage.value * val > props.total) {
        currentPage.value = 1;
    }
    emit("pagination", { page: currentPage.value, limit: val });
}

function handleCurrentChange(val: number) {
    emit("pagination", { page: val, limit: pageSize.value });
}
</script>

<style scoped lang="scss">
.pagination_container {
    background: #fff;
    padding: 32px 16px;
    .hidden {
        display: none;
    }
}
</style>
