package pl.bit4mation.tree.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bit4mation.tree.model.Node;
import pl.bit4mation.tree.service.NodeService;

/**
 * Rest controller for nodes
 * 
 * @author Paweł Rosolak
 */
@Controller
public class TreeController
{

    @Autowired
    private NodeService ns;

    /**
     * @param parentId Parent node id
     * @return List of children
     */
    @RequestMapping( value = { "/nodes/{parentId}" }, method = RequestMethod.GET )
    @ResponseBody
    public List<Node> getNodes( @PathVariable Long parentId )
    {
        List<Node> nodes = ns.findByParentId( parentId );
        return nodes;
    }

    /**
     * @return List of root nodes
     */
    @RequestMapping( value = { "/nodes/root" }, method = RequestMethod.GET )
    @ResponseBody
    public List<Node> getRootNodes()
    {
        List<Node> nodes = ns.getRootNodes();
        return nodes;

    }

    /**
     * Delete node by id
     * 
     * @param nodeId Node id
     */
    @RequestMapping( value = { "/nodes/{nodeId}" }, method = RequestMethod.DELETE )
    @ResponseBody
    public void deleteNode( @PathVariable Long nodeId )
    {
        ns.delete( nodeId );
    }

    /**
     * Save or update node
     * 
     * @param node Node object
     * @return Node id
     */
    @RequestMapping( value = { "/nodes" }, method = RequestMethod.POST )
    @ResponseBody
    public Long save( @RequestBody Node node )
    {
        ns.saveNode( node );
        return node.getId();
    }

}
