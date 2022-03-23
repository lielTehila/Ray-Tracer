package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable{

    private List<Intersectable> shapes;
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> lst=null;
        for(Intersectable i:shapes)
        {
            List<Point> lstHelp=i.findIntersections(ray);
            if(lstHelp!=null)
            {
                if(lst==null)
                    lst=new ArrayList<>();
                lst.addAll(lstHelp);
            }
        }
        return null;
    }
    public Geometries()
    {
        shapes=new ArrayList<Intersectable>();
    }
    public Geometries(Intersectable... geometries) {}
    public void add (Intersectable... geometries){}
}
