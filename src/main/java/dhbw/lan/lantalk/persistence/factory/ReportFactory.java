package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Report;

@Named("reportFactory")
@Dependent
public class ReportFactory extends AFactory<Report> {

	private static final long serialVersionUID = 8172077527412310560L;

	public ReportFactory() {
		super(Report.class);
	}

}
