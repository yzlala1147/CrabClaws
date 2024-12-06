<template>
    <div class="De_serialize">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            ObjectInputStream反序列化
            <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="msg" label="msg" width="180"></el-table-column>
                    <el-table-column prop="data" label="data" width="180"></el-table-column>
                </el-table>

                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="输入payload" style="width: 120px;"></el-input>
                    <el-button @click="fetchData" type="primary">查询</el-button>
                </div>
            </el-main>
        </el-container>
        <el-drawer :visible.sync="drawerVisible" direction="rtl" size="60%" title="Java代码示例">
            <div class="code-block">
                <h3>java源码</h3>
                <pre><code class="java">{{ javaCode }}</code></pre>
            </div>
        </el-drawer>
    </div>
</template>

<script>
import qs from 'qs'; // 引入qs库，用于处理表单数据格式转换
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
    data() {
        return {
            tableData: [],
            inputValue: '',
            drawerVisible: false,
      javaCode: `
    @PostMapping("/Obj")
    public Result Deserialize(@RequestParam("payload") String payload) throws IOException {
        //开启反序列化
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
        try {
            //Url编码会把+号认成空格
            payload= payload.replace(" ", "+");
            byte[] payload_byte = Base64.getDecoder().decode(payload);
            ByteArrayInputStream bytes = new ByteArrayInputStream(payload_byte);
            ObjectInputStream in = new ObjectInputStream(bytes);
            Object o = in.readObject();
            in.close();
            return Result.success(o);
        } catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
      `,
    };
  },
    methods: {
        fetchData() {
            const payload = this.inputValue;
            this.$axios.post('api/serialize/Obj', qs.stringify({ payload: payload }), {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((result) => {
                    this.tableData = [result.data];
                })
              .catch((error) => {
                    console.error('获取数据出错：', error);
                });
        },
        highlightCode() {
      this.$nextTick(() => {
        const codeBlocks = document.querySelectorAll('pre code');
        codeBlocks.forEach((block) => {
          hljs.highlightBlock(block);
        });
      });
    }
    },
    watch: {
    drawerVisible(newVal) {
      if (newVal) {
        this.highlightCode(); // 当抽屉打开时进行高亮显示
      }
    }
  }
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

.input-container {
    margin-top: 20px;
}
.input-button-container {
    display: flex;
    align-items: center;
    margin-top: 20px;
}
.code-block {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f4f4f4;
  border-radius: 8px;
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.ct-button {
  width: 120px;
  height: 40px;
  float: right;
}

.code-block {
  margin-left: 0;
  color: black;
  font-size: 16px;
  text-align: left;
}
</style>