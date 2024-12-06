<template>
    <div class="XXE">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            XXE
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
                    <el-input v-model="inputValue" placeholder="请输入用户名" style="width: 120px;"></el-input>
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
import xmlbuilder from 'xmlbuilder';
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
data() {
    return {
      tableData: [],
      inputValue: '',
      drawerVisible: false,
      javaCode: `
    @PostMapping("/DocumentBuilderFactory")
    public Result XXE_DocumentBuilderFactory(@RequestBody String XXE) {
        try {
            //XXE出发漏洞点
            DocumentBuilderFactory XXE_dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder XXE_db = XXE_dbf.newDocumentBuilder();
            //由于传入XML时会有URL编码，要先对URL进行解码
            String decodedString = URLDecoder.decode(XXE, "UTF-8");
            byte[] bytes = decodedString.getBytes(StandardCharsets.UTF_8);
            //转换成字节 在传入InputStream
            InputStream XXE_Poc = new java.io.ByteArrayInputStream(bytes);
            //传入XML内容 并执行了恶意代码
            Document XXE_d = XXE_db.parse(XXE_Poc);
            //将恶意代码放入字符串
            String username = XXE_d.getElementsByTagName("username").item(0).getTextContent();
            if(username.equals("admin")){
                return Result.success("用户\\"" + username + "\\"存在");
            }else {
                return Result.success("用户\\"" + username + "\\"不存在");
            }
        } catch (Exception e) {
          return  Result.error(e.getMessage());
        }
    }
      `,
    };
  },
    methods: {
        fetchData() {
            const username = this.inputValue;
            // 使用xmlbuilder创建指定格式的XML数据
            const xmlData = xmlbuilder.create('user')
              .ele('username').txt(username).up()
              .end({ pretty: true });

            this.$axios.post('api/XXE/DocumentBuilderFactory', xmlData)
              .then((result) => {
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