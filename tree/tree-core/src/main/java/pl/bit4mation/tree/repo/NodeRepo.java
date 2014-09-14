package pl.bit4mation.tree.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.bit4mation.tree.model.Node;

public interface NodeRepo
    extends CrudRepository<Node, Long>
{

    List<Node> findByParent( Node parent );

    List<Node> findByParentId( Long parentId );
}
