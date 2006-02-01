package org.trails.demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.trails.descriptor.annotation.Collection;
import org.trails.descriptor.annotation.PropertyDescriptor;

@Entity
public class TreeNode
{
    private Integer id;
    
    private String name;
    
    private TreeNode parent;
    
//    private Set<TreeNode> children = new HashSet<TreeNode>();
    
    public TreeNode()
    {
        super();
        // TODO Auto-generated constructor stub
    }

//    @OneToMany
//    @PropertyDescriptor(hidden=true)
//    public Set<TreeNode> getChildren()
//    {
//        return children;
//    }
//
//    public void setChildren(Set<TreeNode> children)
//    {
//        this.children = children;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToOne
    public TreeNode getParent()
    {
        return parent;
    }

    public void setParent(TreeNode parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        try
        {
            final TreeNode many = (TreeNode) obj;
            if (!getId().equals(many.getId()))
                return false;
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return getName();
    }

    
}
