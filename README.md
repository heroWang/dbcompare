数据库比较工具0.1

用于两个数据库之间的比较，目前只关注列的schema比较。

特性：
1.	只支持MySQL数据库
2.	只对数据库的schema进行比较，不支持数据比较
3.	只对表的schema进行比较，不支持视图函数等等。
4.	只对表的列的schema进行比较，不支持表的索引外键等等。
5.	列的schema比较条件包括: 类型/长度/主键否/非空否/自增否/默认值/注释，共7个。
6.	以树的形式展示比较结果。
7.	架构是B/S

使用：
1.	以某种方式得到发布包：ROOT.war。
2.	将ROOT.war部署到某J2EE的web应用服务器，如resin4.0或tomcat7.0。
3.	启动服务器。
4.	打开首页(配置页)如图：
    ![image](http://d.pcs.baidu.com/thumbnail/6c4962b81966620b38dc89dda267ebb9?fid=2836283484-250528-600041877061313&time=1396940270&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-xa%2BYwMYHEThdq%2Bj38DhRnzYkr%2F4%3D&expires=8h&prisign=RK9dhfZlTqV5TuwkO5ihMQzlM241kT2YfffnCZFTaEPwOxHv/XxtwRXLxDSXMBba1Ms9seOiqT9/QffwI8K2Baw0mmLABRQNl51b/oS8+InqoadADmwcyikKawH2SpzFgG52FMuhuBPZ09BZiFo3CF7dwGcK/xIzj9971pKao/QALkDxW+JJC9zJS3FHk0o7bGp1+ccKUCfewiyGFoI0Tj+DVqImScWXew42IholV/Y8ShIS12ddhQ==&r=101714944&size=c850_u580&quality=100)
 
5.	配置好source和target两个数据库
6.	点击confirm保存你的更改，或者点击cancel不保存你的更改。
7.	进入结果页面，加载比较结果树，如图：
    ![image](http://d.pcs.baidu.com/thumbnail/936f1a94acb719509b880d1780c07c85?fid=2836283484-250528-120008460121952&time=1396940270&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-X%2BBATJmL%2B%2FTFJ97aE3doVWPTwnk%3D&expires=8h&prisign=RK9dhfZlTqV5TuwkO5ihMQzlM241kT2YfffnCZFTaEPwOxHv/XxtwRXLxDSXMBba1Ms9seOiqT9/QffwI8K2Baw0mmLABRQNl51b/oS8+InqoadADmwcyikKawH2SpzFgG52FMuhuBPZ09BZiFo3CF7dwGcK/xIzj9971pKao/QALkDxW+JJC9zJS3FHk0o7bGp1+ccKUCfewiyGFoI0Tj+DVqImScWXew42IholV/Y8ShIS12ddhQ==&r=225528398&size=c850_u580&quality=100)

(1)	Tables(Total:3,miss:1,add:1,diff:1) 表示 结果中包含3个表，其中1个是target中有但source中没有的，1个是source中有但target中没有的，1个是两个库中都有，但名字相同具体的列有不同的。
(2)	Columns(Total:5,miss:1,add:1,diff:1) 同Tables。除了了3个不同的列，还有2个列是完全相同的，这里的列相同是指，列的 类型/长度/主键否/非空否/自增否/默认值/注释 7个因素都相同，当然比较这7个因素的前提是列名必须相同。
(3)	每个列有叶节点展示其属性，diff标记的列可以看到在source和target两个库中不同的属性。

8.	重新加载结果树，按F5；重新配置，点击浏览器左上方”后退”。



欢迎以任何形式提供反馈意见
