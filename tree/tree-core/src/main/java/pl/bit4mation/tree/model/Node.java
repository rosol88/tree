package pl.bit4mation.tree.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@SequenceGenerator( name = "id_gen", sequenceName = "node_seq" )
public class Node
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "id_gen" )
    private Long id;

    private int value;

    private boolean leaf = false;

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    @OneToMany( mappedBy = "parent" )
    private Set<Node> children;

    @Transient
    private int sum;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn( name = "parent" )
    private Node parent;

    //
    public int getValue()
    {
        return value;
    }

    public void setValue( int value )
    {
        this.value = value;
    }

    public int getSum()
    {
        return sum;
    }

    public void setSum( int sum )
    {
        this.sum = sum;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent( Node parent )
    {
        this.parent = parent;
    }

    public Set<Node> getChildren()
    {
        return children;
    }

    public void setChildren( Set<Node> children )
    {
        this.children = children;
    }

    @Override
    public String toString()
    {
        return "Node [id=" + id + ", value=" + value + ", leaf=" + leaf + ", parent=" + parent + "]";
    }

    public boolean isLeaf()
    {
        return this.leaf;
    }

    public void setLeaf( boolean leaf )
    {
        this.leaf = leaf;
    }

}
