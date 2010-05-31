package aQute.bnd.build;

import java.io.*;
import java.util.*;

public abstract class ProjectTester {
	final Project				project;
	final Collection<Container>	testbundles;
	final ProjectLauncher		launcher;
	final List<String>			tests		= new ArrayList<String>();
	final File					reportDir;

	public ProjectTester(Project project) throws Exception {
		this.project = project;
		launcher = project.getLauncher();
		testbundles = project.getTestbundles();
		for (Container c : testbundles) {
			if (c.getError() != null)
				project.error(c.getError());
			else
				launcher.addRunBundle(c.getFile());
		}
		reportDir = new File(project.getTarget(), project.getProperty("test-reports", "test-reports"));
		reportDir.mkdirs();
		for (File report : reportDir.listFiles()) {
			report.delete();
		}
	}

	public ProjectLauncher getProjectLauncher() {
		return launcher;
	}

	public void addTest(String test) {
		tests.add(test);
	}

	public Collection<String> getTests() {
		return tests;
	}

	public Collection<File> getReports() {
		List<File> reports = new ArrayList<File>();
		for ( File  report : reportDir.listFiles()) {
			if ( report.isFile())
				reports.add(report);
		}
		return reports;
	}

	public File getReportDir() {
		return reportDir;
	}

	public Project getProject() {
		return project;
	}
	
	public abstract boolean prepare() throws Exception;

	public abstract int test() throws Exception;
}
