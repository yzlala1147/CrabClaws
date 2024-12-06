<template>
    <div class="Log4j2">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">shiro反序列化</div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="msg" label="msg" width="180"></el-table-column>
                    <el-table-column prop="data" label="data" width="180"></el-table-column>
                </el-table>
            </el-main>
        </el-container>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tableData: []
        };
    },
    created() {
        // 在组件创建时（页面加载时）自动发起请求
        this.fetchData();
    },
    methods: {
        fetchData() {
            // 这里可以根据实际需求，决定是否需要传递固定参数等情况，目前示例中没有传递额外参数
            this.$axios.get('api/GetShiroKey')
             .then((result) => {
                    this.tableData = [result.data];
                })
             .catch((error) => {
                    console.error('获取数据出错：', error);
                });
        }
    },
};
</script>

<style scoped>
.SQL1 {
    text-align: center;
}

.main-container {
    padding: 20px;
    background-color: #ffffff;
}
</style>