package pl.bit4mation.tree.service;

import java.util.List;

import pl.bit4mation.tree.model.Node;

/**
 * Provides methods for nodes operations
 * 
 * @author Paweł Rosolak
 */
public interface NodeService
{
    /**
     * @return List of root nodes
     */
    List<Node> getRootNodes();

    /**
     * Search childern of node
     * 
     * @param parentId Parent node id
     * @return list of children
     */
    List<Node> findByParentId( Long parentId );

    /**
     * Get node by id
     * 
     * @param id Node id
     * @return Node object
     */
    Node get( Long id );

    /**
     * Deletes all nodes
     */
    void clean();

    /**
     * Save or update node
     * 
     * @param node Node object
     * @param parentId Id of parent node
     */
    // void saveNode( Node node, Long parentId );

    /**
     * Save or update node
     * 
     * @param node Node object
     */
    void saveNode( Node node );

    /**
     * Delete node by id
     * 
     * @param nodeId Node id
     */
    void delete( Long nodeId );

}
