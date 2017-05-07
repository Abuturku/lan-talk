package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Point;

@Named("pointFactory")
@Dependent
public class PointFactory extends AFactory<Point> {

	private static final long serialVersionUID = 5223761142006971721L;

	public PointFactory() {
		super(Point.class);
	}

}
