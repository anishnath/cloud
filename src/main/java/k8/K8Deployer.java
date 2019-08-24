package k8;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import io.fabric8.kubernetes.api.model.BaseKubernetesListFluent.PodItemsNested;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.EnvVar;

//import io.kubernetes.client.ApiClient;
//import io.kubernetes.client.ApiException;
//import io.kubernetes.client.Configuration;
//import io.kubernetes.client.apis.CoreV1Api;
//import io.kubernetes.client.models.V1Namespace;
//import io.kubernetes.client.models.V1NamespaceBuilder;
//import io.kubernetes.client.models.V1Pod;
//import io.kubernetes.client.models.V1PodList;
//import io.kubernetes.client.proto.V1.Namespace;
//import io.kubernetes.client.util.Config;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.PodStatus;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.extensions.HTTPIngressPath;
import io.fabric8.kubernetes.api.model.extensions.HTTPIngressPathBuilder;
import io.fabric8.kubernetes.api.model.extensions.HTTPIngressRuleValue;
import io.fabric8.kubernetes.api.model.extensions.HTTPIngressRuleValueBuilder;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.api.model.extensions.IngressBackend;
import io.fabric8.kubernetes.api.model.extensions.IngressBackendBuilder;
import io.fabric8.kubernetes.api.model.extensions.IngressBuilder;
import io.fabric8.kubernetes.api.model.extensions.IngressRule;
import io.fabric8.kubernetes.api.model.extensions.IngressRuleBuilder;
import io.fabric8.kubernetes.api.model.extensions.IngressTLS;
import io.fabric8.kubernetes.api.model.extensions.IngressTLSBuilder;
import io.fabric8.kubernetes.client.Callback;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.utils.InputStreamPumper;
import io.kubernetes.client.util.Yaml;

public class K8Deployer {
	private static final Logger logger = LoggerFactory.getLogger(K8Deployer.class);
	
	String javaHome = System.getenv("JAVA_HOME");
	
	private static final String master = System.getenv("MASTER");
	private static final String cacertdata = System.getenv("CACERTDATA");
	private static final String clinetkeydata = System.getenv("CLIENTKEYDATA");
	private static final String clinetcertData = System.getenv("CLIENTCERTDATA");
	//private static final String namespace = System.getenv("NAMESPACE");
	private static final String dns = System.getenv("DNS");
	
	

	public static void main(String[] args) throws Exception {
		
		
		//Playground.getAccessToken("playground");
		
		//deletePodsExpired("playground");
		
		Playground.launchPython3("playground");
		
		//ExecPipesExample.main(null);
		
		
//		for (int i = 0; i < 3; i++) {
//			
//			String podName =  RandomStringUtils.randomAlphabetic(20).toLowerCase();
//			createPod(podName,"nginx","playground");
//			
//		}
		
		
		//deletPod("demopod","thisisatest1");
		
		System.exit(1);
		
		// ApiClient client = Config.fromConfig("config");
		// client.setVerifyingSsl(false);
		// Configuration.setDefaultApiClient(client);
		//
		// CoreV1Api api = new CoreV1Api();
		// V1PodList list = api.listPodForAllNamespaces(null, null, null, null,
		// null, null, null, null, null);
		// for (V1Pod item : list.getItems()) {
		// System.out.println(item.getMetadata().getName());
		// }
		//
		//
		// // api.withNa
		//
		// V1Namespace ns = new
		// V1NamespaceBuilder().withNewMetadata().withName("thisisatest").addToLabels("this",
		// "rocks").endMetadata().build();
		//
		// api.createNamespace(ns, null, null, null);
		//
		//
		//
		// System.out.println(ns);

		// deploy();

		// log("Created namespace", client.namespaces().create(ns));

		String namespace = "thisisatest1";
		String username = "username";
		String imagename = "nginx";
		String image = "nginx:latest";

		int port = 8080;

		String deploymentname = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		String label = RandomStringUtils.randomAlphabetic(10).toLowerCase();

		String host = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "."+dns;

		System.out.println(deploymentname);
		System.out.println(label);
		System.out.println(host);
		
		List<String> commandList =  new ArrayList<>();
		
		//commandList.add("ls -ltr");
		//commandList.add("ls");
		
		List<String> conatinerArgs = new ArrayList<>();
		
		//conatinerArgs.add("-ltr");

		
		List<String> envvar = new ArrayList<>();
		
		envvar.add("A=A");
		envvar.add("B=B");

		// deploymentname = "yjtyomegqf";
		// label = "siisoydkge";

		// deleteNS(namespace);
		//deploy(namespace, username, imagename, image, deploymentname, label, commandList, conatinerArgs, envvar, host, port);

		
		deleteDeployment(namespace, "yvxdbayfva");
		
		//getLogs(namespace, label);
		//execToPods(namespace, label);
		
		//dumpDeployment(namespace, username, imagename, image, deploymentname, label, host, port);

	}
	
	
	public static void deletePodsExpired(String ns) throws Exception
	{
		
		final KubernetesClient client = getClient();
		
		PodList podlist = client.pods().inNamespace(ns).list();
		
		if(podlist!=null)
		{
			List<Pod> poditems = podlist.getItems();
			
			for (Iterator iterator = poditems.iterator(); iterator.hasNext();) {
				Pod pod = (Pod) iterator.next();
				
				String creationTimestamp = pod.getMetadata().getCreationTimestamp();
				
				System.out.println("SelfLink on == " + pod.getMetadata().getSelfLink());
				
				System.out.println("Created on == " + creationTimestamp);
				
				
			}
			
		
		}
		
		client.close();
		
	}
	
	
	
	public static void createPod(String podName, String imageName, String ns) throws Exception
	{
		
		final KubernetesClient client = getClient();
		
		ObjectMeta meta = new ObjectMetaBuilder()
				.withNewName(podName)
				.build();
		
		Container containers = new ContainerBuilder()
				.withImage(imageName)
				.withName(podName)
				.build();
		
		Pod pod = new PodBuilder()
				.withMetadata(meta)
				.withKind("Pod")
				.withNewSpec()
				.withContainers(containers)
				.endSpec()
				.build();
		
		
		System.out.println(Yaml.dump(pod.toString()));
		
		
		
		 pod   = client.pods().inNamespace(ns).create(pod);
		 
		 
		 
		
		client.close();
		
	}
	
	public static void deletPod(String podName,String ns) throws Exception
	{
		
		final KubernetesClient client = getClient();		
	    client.pods().inNamespace("thisisatest1").withName(podName).delete();
		
		client.close();
		
		
	}
	
	
	
	
	public static void createNS(String username) throws Exception
	{
		final KubernetesClient client = getClient();

		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			String ns1 = "fixedsalt" + username;
			
			
			
	        byte[] hashInBytes = md.digest(ns1.getBytes(StandardCharsets.UTF_8));
	        
	        String hash = Hex.encodeHexString(hashInBytes).toLowerCase();
	        
	        String userNameHash = Hex.encodeHexString(username.getBytes(StandardCharsets.UTF_8)).toLowerCase();
	        
			
			Namespace ns = client.namespaces().withName(hash).get();

			if (ns == null) {
				ns = new NamespaceBuilder().withNewMetadata().withName(hash).addToLabels("user", userNameHash)
						.endMetadata().build();
				client.namespaces().create(ns);
			}
			
			client.close();
		} catch (Exception e) {
			throw e;
		}
		
	}
	

	private static void deleteNS(String namespace) {
		final KubernetesClient client = getClient();
		client.namespaces().withName(namespace).delete();
		client.close();
	}

	private static void getLogs(String namespace, String label)

	{

		final KubernetesClient client = getClient();
		System.out.println(
				client.pods().inNamespace(namespace).withName("zrztlpamrx-79dcc5bff9-7qtst").tailingLines(10).getLog());

	}

	
	public static boolean isPodRunning(String namespace, String username, String imagename, String image, String deploymentname,
			String label, String host, int port)
	{
		
		final KubernetesClient client = getClient();
		
		
		//client.apps().deployments().inNamespace(namespace).withName(deploymentname);
		
		
		PodList podlist = client.pods().inNamespace(namespace).withLabel("app",label).list();
		
		
		
		List<Pod> listPods= podlist.getItems();
		
		System.out.println("listPods  " + listPods.size());
		
		for (Iterator iterator = listPods.iterator(); iterator.hasNext();) {
			Pod pod = (Pod) iterator.next();
			
			PodStatus podStatus = pod.getStatus();
			
			
			
			List<ContainerStatus> containerStatus = podStatus.getContainerStatuses();
			
			for (Iterator iterator2 = containerStatus.iterator(); iterator2.hasNext();) {
				ContainerStatus containerStatus2 = (ContainerStatus) iterator2.next();
				
				System.out.println(containerStatus2);
				
				return containerStatus2.getReady();
				
			}
			
			
			
		}
		return false;
		
	}
	
	
	
	private static void execToPods(String namespace, String label)
    {


        
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try (
        		final KubernetesClient client = getClient();
                ExecWatch watch = client.pods().inNamespace(namespace).withName("zrztlpamrx-79dcc5bff9-7qtst")
                        .redirectingInput()
                        .redirectingOutput()
                        .redirectingError()
                        .redirectingErrorChannel()
                        .exec();
        		
        		
                InputStreamPumper pump = new InputStreamPumper(watch.getOutput(), new SystemOutCallback()))
        {

            executorService.submit(pump);
            watch.getInput().write("ps -elf\n".getBytes());
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
            throw KubernetesClientException.launderThrowable(e);
        } finally {
            executorService.shutdownNow();
        }
    	
    }
	
	private static class SystemOutCallback implements Callback<byte[]> {
        @Override
        public void call(byte[] data) {
            System.out.print(new String(data));
        }
    }


	
	public static void deleteDeployment(String namespace,String deploymentname)
	{
		final KubernetesClient client = getClient();
		
		
		Deployment newDeployment = new DeploymentBuilder()
				.withNewMetadata()
				.withName(deploymentname)
				.withNamespace(namespace)
				.endMetadata()
				.withNewSpec()
				.withReplicas(1)
				.withNewTemplate()
				.withNewMetadata()
				.endMetadata()
				.endTemplate()
				.endSpec()
				.build();
		
		client.apps().deployments().inNamespace(namespace).delete(newDeployment);
		
		
		Ingress ingress = new IngressBuilder().withApiVersion("extensions/v1beta1").withKind("Ingress")
				.withNewMetadata().withName(deploymentname + "ingress").endMetadata()
				.build();
		
		client.extensions().ingresses().inNamespace(namespace).delete(ingress);
		
		
		
	}

	public static void deploy(String namespace, String username, String imagename, String image, String deploymentname,
			String label, List<String> commandList, List<String> conatinerArgs, List<String> envvar, String host, int port) {

		final KubernetesClient client = getClient();

		int[] ports = { 80 };
		Namespace ns = client.namespaces().withName(namespace).get();

		if (ns == null) {
			ns = new NamespaceBuilder().withNewMetadata().withName(namespace).addToLabels("user", username)
					.endMetadata().build();
			client.namespaces().create(ns);
		}

		// final List<ContainerPort> containerPorts = new
		// ArrayList<>(ports.length);
		// for (int port : ports) {
		// containerPorts.add(new
		// ContainerPortBuilder().withContainerPort(port).build());
		// }

		// Container containerBuilder = new ContainerBuilder()
		// .withName(imagename)
		// .withImage(image)
		// .withPorts(containerPorts)
		// .build();
		
		List<EnvVar> envVarList = new ArrayList<>();
		
		for (Iterator iterator = envvar.iterator(); iterator.hasNext();) {
			String hold = (String) iterator.next();
			
			String[] tmp = hold.split("=");
			
			EnvVar enVars = new EnvVar();
			
			
			
			enVars.setName(tmp[0]);
			enVars.setValue(tmp[1]);
			
			envVarList.add(enVars);
			
		}
		
		

		Deployment newDeployment = new DeploymentBuilder()
				.withNewMetadata()
				.withName(deploymentname)
				.withNamespace(namespace)
				.endMetadata()
				.withNewSpec()
				.withReplicas(1)
				.withNewTemplate()
				.withNewMetadata()
				.addToLabels("app", label)
				.endMetadata()
				.withNewSpec()
				.addNewContainer()
				.withName(label)
				.withImage(image)
				.addNewPort()
				.withContainerPort(port)
				.endPort()
				.withCommand(commandList)
				.withArgs(conatinerArgs)
				.withEnv(envVarList)
				.endContainer()
				.endSpec()
				.endTemplate()
				.withNewSelector()
				.addToMatchLabels("app", label)
				.endSelector()
				.endSpec()
				.build();

		newDeployment = client.apps().deployments().inNamespace(namespace).create(newDeployment);
		log("Created deployment", newDeployment);

		HashMap<String, String> selector = new HashMap<>();
		selector.put("app", label);

		Service service = new ServiceBuilder().withNewMetadata()
				.withName(deploymentname).endMetadata().withNewSpec().withPorts(new ServicePortBuilder().withPort(port)
						.withNewTargetPort().withIntVal(port).endTargetPort().build())
				.withSelector(selector).endSpec().build();

		service = client.services().inNamespace(namespace).create(service);
		log("Created service with name ", service.getMetadata().getName());

		// System.out.println(containerBuilder);
		System.out.println(newDeployment);
		System.out.println(service);
		
		if(!image.contains("mysql") || !image.contains("postgress") || !image.contains("busybox") || !image.contains("mariadb") )
			
		{	
		
		

		HashMap<String, String> annotations = new HashMap<>();
		// annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/$1");
		// annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
		annotations.put("kubernetes.io/ingress.class", "nginx");
		annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
		annotations.put("nginx.ingress.kubernetes.io/force-ssl-redirect", "false");

		String path = "/";
		
		IngressTLS ingressTLS = new IngressTLSBuilder().withSecretName("0cloud0-wildcard-certs").withHosts(host).build();

		IngressBackend backend = new IngressBackendBuilder().withNewServiceName(deploymentname).withNewServicePort(port)
				.build();

		HTTPIngressPath httpIngressPath = new HTTPIngressPathBuilder().withPath(path).withBackend(backend).build();

		HTTPIngressRuleValue httpingressrulebuilder = new HTTPIngressRuleValueBuilder().withPaths(httpIngressPath)
				.build();

		IngressRule ingressrule = new IngressRuleBuilder().withHost(host).withHttp(httpingressrulebuilder).build();

		Ingress ingress = new IngressBuilder().withApiVersion("extensions/v1beta1").withKind("Ingress")
				.withNewMetadata().withName(deploymentname + "ingress").withAnnotations(annotations).endMetadata()
				.withNewSpec().withRules(ingressrule).withTls(ingressTLS).endSpec().build();

		ingress = client.extensions().ingresses().inNamespace(namespace).create(ingress);
		log("Created Ingress with name ", ingress.getMetadata().getName());

		System.out.println(ns);
		
		}
	}

	public static KubernetesClient getClient() {
		
		
		
		

//		String master = "https://10.64.83.49:6443";
//		String cacertdata = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUN5RENDQWJDZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRFNU1EY3pNVEV4TkRZeU1Wb1hEVEk1TURjeU9ERXhORFl5TVZvd0ZURVRNQkVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBT1p6CnU5Sk8xYUhNSy9kM3cvbzRvOTZiMTJUU09BME5ZSXNFZ2N3bno3bTF1TW1XakhNR0dDZGtGWWFEVGlpVm5rMWcKTFJ6ZGpYd1k0b1hLZkoxVkNXazdGOFNHTnR2U0xHa0pZVUNtcmVNRng5TnNJREdUK0FqK0h0WG41WG5BWEpXUwpVektVVVRBSFZoVzV3UndmbldzY0FZWjN4MFlHY3UvclVVOXVkb0JWWjN5dVE3VzNsWHpSUDJlZ1MvUkF0dDUrCnl6ZWhlWXN4S1hXelVidlhON0U2dWRQbVlnWFFZMUgwR1FKaStmdVpWVXJkVVNnVXQ4cXVuZ2V4dmZrUEU2USsKRnpjTWRtdlpZUFRtMWVEdnZqU0tEOHpLUVliK0VFdVd3U0dPMmlKcVMwRmEzVGF6TGMwR2VqeHkwUGhSOU9FeQo5M0x4NE1BQkxTUjk4Z2k1SjlVQ0F3RUFBYU1qTUNFd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU1CQWY4d0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFHVWR2SmVRbXFXdGpRLzE3ek4rOUhsM0V5S2YKVDVWYVgyYlFiYWg1TVlYNXdjZy9uVWdRVi9EekJCTElKbVhCR3lQZ0d0MHRJSFJwZFowVGRyVGwwUDlia1FDcwpFeS9hUkh2SVc3KzZjdE9YM2dhU0NpZFA0aEJtanZtTU1RTHZxd2xORTk5a2FYRnNoWXNmelZYaFpaM2MrK0VvCmNueWFWdk5kd2dobTMwWUVKaFMraG1JYVJlQ3RZZjllVDJSUmxQOGN3K0V2cUNRbFRNbEQ2clpLMmtkSGNDTHoKQjE5SzcvUFFtOVJMSUd0VnVuOUgzTjNNVUx3aWtwTFpDdGJWcVQ1aldxeUU1cXByd3hLWEs1djlHSWovQWxRUwpzK01uaWFLZllqYmMwSy9UODlsSDhmUlM5MlY2Z0dOR3F6aUpXSDBLaEx2VGdRMURsK0RYT1BOcXJpVT0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=";
//		String clinetkeydata = "LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFcEFJQkFBS0NBUUVBKzdQVmZkOEtWMGh0LytoWHJFbHdlNFlkSmtOVlIrdW96d1U2ZGtMdWFLbnV4bkI1CmtGTDMvVEw4M2VaNHhmUFQ4Sm9UL2R0Q3FqUWtDOGJoL1RWTWhmV0lrNDRXTVJVNlJaWVluY3VmUVJBSStvZVcKWHM1QUFyMWtNVzVxdGRtaGJaQnlSTW51WmUxSFRacnFITlFiNzNUUHcvREthQmgyWkZ5K3FoY0p1ZWI1dkpuQwp0NjVzQVRHUW8wL1BJcXdRVGlXelcwY1RLclE0UDN1aHE2eDAwZHNJeDJHNVRRVmx0UW1rZkU5dVRHMnNTcXdrCmhXQWw4MW11dVZ3SEpYMzRsNjFDU0NGNGdPVTlZc1FudXRBWlJJaFZkbldvWUpmNllKM01GL1VLK1lsb1JNZnUKOTNwdnN5QmlsUXNVaG9GMUZqdVRKUUpIcGdyaW80R0VoTkFnR3dJREFRQUJBb0lCQUU5RU9DUzJ2QXYyekdWKwpKellqdnArNEFiQU9IbXBTNWN1a2F0L1pmamxKNHQ5dGhtc1VKZk1BUHJBbko3dTNhUFJwb1lnZHArWHdHN1gxCndTKzd2RWlucFlaS2hlU1pPY3UyVkRSRmJ2YlBkUFJwQk1zQWl4b1J5TjdrVmdqRTY5aVVWOWdmKzhydnFiZmYKNUlDRnIvSitSL3Y5OWRCSWUraHBhTVlxRVM4UTFZdjVyVVdFZTFlamdNM29UZktjVHFTZnNYcWtBQ2lvaWFBOQpCMThaUy9YZDBjWnFSN1lablVIbSt5Q1pFK0Ria05IdTFHTkNTWk0yN0ZnZm9qTkJSUFZ5VFV0T213bTNDWlNyCm94dWpmSEhzcEVqbUtjMUVjTXhOMW9FbFkzWDZCMmVSek8rNzV0ZElTcEtreVBVRFZkZ3hweU1CSGlTWHFZQjgKMit2bUs4RUNnWUVBLzhYNzFCNVdNOWFtSk9WMjlWeWZSUmVEcndTdGJxNWtzU1ordjZWSnM0U2JjVnIzU2xneQpSVlZ2V3BKQkJRcEl4VmcxWW5BYlFlQTRXUDdjYmNhRmZqUWYwdDJCOTNOcHV1WUR5V3NXYys4WmRUelM2VXF1ClhUTTBrZWJ1SWdKaFU5MDdCbDFPTnZiUVZLK1pmZjhYeFJsRDlOc1RaQXpsREtCUVBPWU5MS0VDZ1lFQSsrenQKUm9PWGNWdXdvNDJibEFRcUlCcnVaYzFyOHlSRjM4WkNhUlpYVGtTT1h6SFI2bnJubWJ5OU41bG5wbDlKang5cgpWSkhYOEZraGFpNS9oVXB2eVdFUmliRkFRSFp0NHdRNy83UjVHN3Yvak5EaGcwdGVsNnhlRVB5U3RHR3l3c0dECkRjVCtDUmMzd2RtWC9xeEY1R01oWUtKZVQwZEN6MU0wVFJzSWR6c0NnWUJqZUF5Q3ByV2hmVldwdmE5d3BtK3UKUUFIUE84Tnk2dEZ2NzJlVmdtTlNORHo3YllRRVhwNVdSbTZDeTZRTG9MOUNPQmF5ektSZER5VDZ2MHJjU2pzNgpKRzFINVNzWkg4STdsQjVlNDQyYzBweVU4bTJYbXBxSzFsSVlkN1dlZGt5QmYrVG5pYnZVTmxJNHpQMmxlV0kyCkhhaEcvamZoVGY1NTFaSyt6RHpOWVFLQmdRQy8zODA4UnArQ3hCMytBNjI2QjQzNkUrajgrbjg4bWFUcWNidEYKQkxJTU42VGtGakxXVHpPMFlROFFtTE5jQVBrcW0vaUlXZWhJOCtuQ1VKWHBkQzgrQnNOdnh1T3o5VDl1aDF3WApQbUo3WUMyY0Y3K2EyejI1Y1p2WGZqZFNVWkx3U0tqRFJRVmk4UjhUY3VJVUl5dThibEFPcEx3Rno3V3Y2S1JPCkVjZlozd0tCZ1FEMW1XOUdaSkRmWmw3bmw2cENVdVMzU1F1M2tPRGFNL09IbDBwREFuMi9WOWJiWjBmQ2N3V0EKYnFsMHJZVnZDN1F3S2VhMVUxU1F5dnltRDlOUUNGdEFTbzJjYkZiVHliclZPQkUySkJ4Y01QZEdtSzRxalA0dwpXSy8yNHBGOFlDMVpHcGx2bGU2dUdWamkrNzhnMHZYQzAzeFhMdTVhS3RoYy9pbWMxd0xQaFE9PQotLS0tLUVORCBSU0EgUFJJVkFURSBLRVktLS0tLQo=";
//		String clinetcertData = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM4akNDQWRxZ0F3SUJBZ0lJVFowalcwRGhnKzh3RFFZSktvWklodmNOQVFFTEJRQXdGVEVUTUJFR0ExVUUKQXhNS2EzVmlaWEp1WlhSbGN6QWVGdzB4T1RBM016RXhNVFEyTWpGYUZ3MHlNREEzTXpBeE1UUTJNakphTURReApGekFWQmdOVkJBb1REbk41YzNSbGJUcHRZWE4wWlhKek1Sa3dGd1lEVlFRREV4QnJkV0psY201bGRHVnpMV0ZrCmJXbHVNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQSs3UFZmZDhLVjBodC8raFgKckVsd2U0WWRKa05WUit1b3p3VTZka0x1YUtudXhuQjVrRkwzL1RMODNlWjR4ZlBUOEpvVC9kdENxalFrQzhiaAovVFZNaGZXSWs0NFdNUlU2UlpZWW5jdWZRUkFJK29lV1hzNUFBcjFrTVc1cXRkbWhiWkJ5Uk1udVplMUhUWnJxCkhOUWI3M1RQdy9ES2FCaDJaRnkrcWhjSnVlYjV2Sm5DdDY1c0FUR1FvMC9QSXF3UVRpV3pXMGNUS3JRNFAzdWgKcTZ4MDBkc0l4Mkc1VFFWbHRRbWtmRTl1VEcyc1Nxd2toV0FsODFtdXVWd0hKWDM0bDYxQ1NDRjRnT1U5WXNRbgp1dEFaUkloVmRuV29ZSmY2WUozTUYvVUsrWWxvUk1mdTkzcHZzeUJpbFFzVWhvRjFGanVUSlFKSHBncmlvNEdFCmhOQWdHd0lEQVFBQm95Y3dKVEFPQmdOVkhROEJBZjhFQkFNQ0JhQXdFd1lEVlIwbEJBd3dDZ1lJS3dZQkJRVUgKQXdJd0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFPWlJIY2VxUVloV2R3dGlVSTRIcHpDMktkMUp2amIzRWNKUAp3T2hyNS9iOVFialhlVUdvNEVxaUtnUGVrZmxTUmJ4Rzd4eXZ6dW1nQ24wdWVuTGJnWnhCOEhkTFMvcm9HQTlWCmNTZ3g3RzUzZkF2c1pDZkR3dWxIeUFNaWlVeGIybVMyeUd6dlEvVmIyekMvZzJTT0JVcjJEMWlvQjZldGtGZnEKbG9HQmdxK01NRUVCKzFmWU1kWTNBUSttYWhwTzM5R0dmc2cybTl4OW9mUHNwdmRNK2Ezak9admxKMm1PdlFYawppbUNMaVYvQm5OTkY1Z1k2ZDEzOVVuTDB1TllSSFNxdHJYUjBHUmROK0g3ajdnMmtraTZHMkV0UElmdDZ0WWYzCnI0ZEJzUDlGRVBNTnFGNkZ3c1ZjcUVpWWg2eVh0TUVVRHZmTlpxK2J2VWNuRnJoTkxTMD0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=";
		
		System.out.println("MASTER URL " + master);
		
		Config config = new ConfigBuilder().withMasterUrl(master).withClientKeyData(clinetkeydata)
				.withClientCertData(clinetcertData).withCaCertData(cacertdata).withDisableHostnameVerification(true)
				.withTrustCerts(true)
				// .withUsername("kubernetes-admin")
				.build();

		final KubernetesClient client = new DefaultKubernetesClient(config);
		return client;
	}

	private static void log(String action, Object obj) {
		logger.info("{}: {}", action, obj);
	}

	private static void log(String action) {
		logger.info(action);
	}
}