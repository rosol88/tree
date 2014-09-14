package pl.bit4mation.tree.service;

import java.util.Collection;
import java.util.List;

import pl.bit4mation.tree.model.Node;

public interface NodeService
{

    List<Node> getRootNodes();

    Node createNode( Node node );

    Node get( Long id );

    void clean();

    void save( Collection<Node> nodes, Node parent );

    void save( Node node, Long parentId );

    void delete( Long nodeId );

}
