<template>
    <div class="home-page">
      <div class="header-text" style="color: black">
        <h3>欢迎来到蟹钳靶场，该靶场通过springboot+Vue实现的一款综合型Java靶场</h3>
        <h3>支持漏洞模块和springboot组件：</h3>
      </div>
      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%">
        <!-- 循环生成5列 -->
        <el-table-column v-for="(column, index) in columnHeaders" :key="index" :prop="column.prop" :label="column.label" width="180"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        tableData: [
          { col1: '布尔注入', col2: 'exec', col3: 'ObjectInputStream', col4: '任意文件上传', col5: 'Swagger' },
          { col1: '布尔盲注', col2: 'ProcessBuilder', col3: 'fastjson', col4: '任意文件读取', col5: 'druid' },
          { col1: '延时盲注', col2: 'SpEL', col3: 'log4j', col4: 'SSRF', col5: 'actuator' },
          { col1: '',        col2: 'SSTI', col3: 'shiro', col4: 'XXE', col5: '' }
        ],
        columnHeaders: [
          { prop: 'col1', label: 'SQL注入' },
          { prop: 'col2', label: '命令注入' },
          { prop: 'col3', label: '反序列化漏洞' },
          { prop: 'col4', label: '其他常见漏洞' },
          { prop: 'col5', label: 'Springboot组件' }
        ]
      };
    }
  };
  </script>
  
  <style scoped>
  .home-page {
    padding: 20px;
  }
  
  .header-text {
    font-size: 18px;
    margin-bottom: 20px;
    text-align: center;
  }
  
  .el-table {
    margin-top: 10px;
  }
  </style>