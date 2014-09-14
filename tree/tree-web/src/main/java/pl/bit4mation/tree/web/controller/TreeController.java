package pl.bit4mation.tree.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bit4mation.tree.model.Node;
import pl.bit4mation.tree.repo.NodeRepo;
import pl.bit4mation.tree.service.NodeService;

@Controller
public class TreeController
{
    private static Logger log = LoggerFactory.getLogger( TreeController.class );

    @Autowired
    private NodeService ns;

    @Autowired
    private NodeRepo repo;

    @RequestMapping( value = { "/nodes/{parentId}" }, method = RequestMethod.GET )
    @ResponseBody
    public List<Node> getNodes( @PathVariable Long parentId )
    {
        List<Node> nodes = repo.findByParentId( parentId );
        return nodes;

    }

    @RequestMapping( value = { "/nodes/root" }, method = RequestMethod.GET )
    @ResponseBody
    public List<Node> getRootNodes()
    {
        List<Node> nodes = ns.getRootNodes();
        return nodes;

    }

    // @RequestMapping( value = { "/node" }, method = RequestMethod.POST )
    // @ResponseBody
    // public void save( @RequestBody List<Node> nodes )
    // {
    // ns.save( nodes, null );
    // }

    @RequestMapping( value = { "/nodes/{nodeId}" }, method = RequestMethod.DELETE )
    @ResponseBody
    public void deleteNode( @PathVariable Long nodeId )
    {
        ns.delete( nodeId );
    }

    @RequestMapping( value = { "/nodes" }, method = RequestMethod.POST )
    @ResponseBody
    public Long save( @RequestBody Node node )
    {
        log.debug( "Node: {}", node );
        Long parentId = null;
        if ( node.getParent() != null )
            parentId = node.getParent().getId();
        ns.save( node, parentId );
        return node.getId();
    }

}
