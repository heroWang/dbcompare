package com.sohu.assist.dbcompare.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sohu.assist.dbcompare.model.json.JsonNode;
import com.sohu.assist.dbcompare.model.result.BaseLeafResult;
import com.sohu.assist.dbcompare.model.result.BaseLimbResult;
import com.sohu.assist.dbcompare.model.result.BaseResult;
import com.sohu.assist.dbcompare.model.result.ColumnResult;
import com.sohu.assist.dbcompare.model.result.ColumnsResult;
import com.sohu.assist.dbcompare.model.result.DiffContent;
import com.sohu.assist.dbcompare.model.result.Result;
import com.sohu.assist.dbcompare.model.result.TableResult;
import com.sohu.assist.dbcompare.model.result.TablesResult;
import com.sohu.assist.dbcompare.resource.SCHEMATYPE_IMAGE;

public class Result2JsonNode {
    private final static String RESOURCE_DIR = "skin/dynatree/";
    private final static Map<Class<? extends Result>, String> nodeNameMap = new HashMap<Class<? extends Result>, String>();
    private final static Map<Class<? extends Result>, SCHEMATYPE_IMAGE> nodeImageMap = new HashMap<Class<? extends Result>, SCHEMATYPE_IMAGE>();
    static {
        nodeNameMap.put(TablesResult.class, "Tables");
        nodeNameMap.put(ColumnsResult.class, "Columns");

        nodeImageMap.put(TablesResult.class, SCHEMATYPE_IMAGE.TABLES);
        nodeImageMap.put(TableResult.class, SCHEMATYPE_IMAGE.TABLE);
        nodeImageMap.put(ColumnsResult.class, SCHEMATYPE_IMAGE.COLUMNS);
        nodeImageMap.put(ColumnResult.class, SCHEMATYPE_IMAGE.COLUMN);
    }

    public static JsonNode result2JsonNode(BaseResult result,String basePath) {


        JsonNode resultNode = new JsonNode();

        //set Tables Columns node expanded
        if(result.isPackage()){
            resultNode.setExpand(true);
        }

        resultNode.setIcon(nodeImageMap.get(result.getClass()).getRes());
        resultNode.setTitle(genericTitle(result,basePath));

        List<Result> childrenResultList = result.getChildren();
        List<JsonNode> childrenNodeList = new ArrayList<JsonNode>();
        if (childrenResultList != null) {
            for (Result r : childrenResultList) {
                JsonNode node = result2JsonNode((BaseResult) r,basePath);
                childrenNodeList.add(node);
            }
        } else {
            if (result.isLeaf()) {
                resultNode.setFolder("false");
                JsonNode diffContentNode = new JsonNode();

                diffContentNode.setIcon(SCHEMATYPE_IMAGE.CONTENT.getRes());
                BaseLeafResult leafResult = (BaseLeafResult) result;
                DiffContent content = leafResult.getDiffContent();
                switch (leafResult.getResultType()) {
                    case ADD:
                    case EQUAL:
                        diffContentNode.setTitle(content.getSourceCode());
                        break;
                    case MISS:
                        diffContentNode.setTitle(content.getTargetCode());
                        break;
                    case MODIFY:
                        diffContentNode.setTitle(content.getSourceCode() + "<br/>" + content.getTargetCode());
                        break;
                    default:
                        break;
                }
                childrenNodeList.add(diffContentNode);
            }
        }

        resultNode.setChildren(childrenNodeList);
        return resultNode;
    }



    // private static final String doubleQuot(String str) {
    // return "\"" + str + "\"";
    // }
    //
    private static final String quot(String str) {
        return "'" + str + "'";
    }

    private static String genericTitle(BaseResult result,String basePath) {
        StringBuffer title = new StringBuffer();
        String resultImage = result.getResultType().getIMAGE().getRes();
        String nodeName = nodeNameMap.get(result.getClass()) != null ? nodeNameMap.get(result.getClass()) : result
                .getName();

        title.append("<img src=");
        title.append(quot(basePath+"/"+RESOURCE_DIR + resultImage));
        title.append("/>");
        title.append(nodeName);
        if (result.isPackage()) {
            BaseLimbResult limbResult = (BaseLimbResult) result;

            title.append("(Total:" + limbResult.getTotalCount());
            title.append(",miss:" + limbResult.getMissCount());
            title.append(",add:" + limbResult.getAddCount());
            title.append(",diff:" + limbResult.getDiffCount());
            title.append(")");

        }
        return title.toString();
    }
}
