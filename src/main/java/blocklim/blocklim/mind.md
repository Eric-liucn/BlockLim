* [x] 生成配置
* [x] 服务器初始化时建立一个Map，包含所有限制规则
* [x] Map格式：Map<String,ChildMap>,ChildMap<String,String>
* [x] 监听方块放置事件，获取该方块id
* [ ] 如果玩家有bypass权限
* [ ] 获取放置该方块的区块
* [ ] 遍历该区块的所有方块
* [ ] 获取每个方块的id 并 获取该物品的UnsafeDamage
* [ ] 计算某种特定方块的数量
* [ ] 跟配置文件中的限制数量进行对比
* [ ] 如果该方块数量大于，等于限制
* [ ] 取消该放置事件

配置文件结构

    Rules{
        rule 1{
            BlockId: "minecraft:glass"
            Limit: 10         
        }
        rule 2{
            BlockId: "minecraft:wood"
            Limit: 10         
        }        

    }
