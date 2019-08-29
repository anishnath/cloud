package k8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.ObjectReference;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.api.model.SecretList;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
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
import io.fabric8.kubernetes.api.model.rbac.Role;
import io.fabric8.kubernetes.api.model.rbac.RoleBinding;
import io.fabric8.kubernetes.api.model.rbac.RoleBindingBuilder;
import io.fabric8.kubernetes.api.model.rbac.RoleBuilder;
import io.fabric8.kubernetes.api.model.rbac.RoleRef;
import io.fabric8.kubernetes.api.model.rbac.RoleRefBuilder;
import io.fabric8.kubernetes.api.model.rbac.Subject;
import io.fabric8.kubernetes.api.model.rbac.SubjectBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.kubernetes.client.util.Yaml;

public class Playground {
	
	private static final String dns = System.getenv("DNS");
	

	
	
	public static void getSelfLink()
	{
		
	}
	
	public static String launchPlaygroundPods(String ns,String image) throws Exception
	{

		String podName =  RandomStringUtils.randomAlphabetic(20).toLowerCase();
		int port =5000;
		String imageName = PlaygroundConstants.imageMap.get(image);
		
		if(imageName==null)
		{
			return null;
		}
		
		
		
		final KubernetesClient client = K8Deployer.getClient();
			
		ObjectMeta meta = new ObjectMetaBuilder()
				.withNewName(podName)
				.addToLabels("app", podName)
				.build();
		
		List<String> argsList =   null;
		Container containers =null;
		if(image.equals("python3"))
		{
			
			argsList =   new ArrayList<String>();
			argsList.add("--command");
			argsList.add("python3");			
			containers = new ContainerBuilder()
					.withImage(imageName)
					.withName(podName)
					.withImagePullPolicy("Always")
					.withCommand("pyxtermjs")
					.withArgs(argsList)
					.addNewPort()
					.withContainerPort(port)
					.endPort()
					.build();
		}
		
		else if (image.equals("mariadb10"))
		{
			containers = new ContainerBuilder()
					.withImage(imageName)
					.withName(podName)
					.withImagePullPolicy("Always")
					.addNewPort()
					.withContainerPort(port)
					.endPort()
					.build();	
		}
		
		else{
			
			argsList =   new ArrayList<String>();
			argsList.add("--command");
			argsList.add("sh");			
			containers = new ContainerBuilder()
					.withImage(imageName)
					.withName(podName)
					.withImagePullPolicy("Always")
					.withCommand("pyxtermjs")
					.withArgs(argsList)
					.addNewPort()
					.withContainerPort(port)
					.endPort()
					.build();
			
		}
		
		
		Pod pod = new PodBuilder()
				.withMetadata(meta)
				.withKind("Pod")
				.withNewSpec()
				.withContainers(containers)
				.endSpec()
				.build();
		
		 pod   = client.pods().inNamespace(ns).create(pod);
		 
		 Thread.sleep(1000);
		
		
		HashMap<String, String> selector = new HashMap<>();
		selector.put("app", podName);
		
		Service service = new ServiceBuilder()
				.withNewMetadata()
				.withName(podName)
				.endMetadata()
				.withNewSpec()
				.withPorts(new ServicePortBuilder().withPort(port)
						.withNewTargetPort().withIntVal(port).
						endTargetPort().build())
				.withClusterIP("None")
				.withSelector(selector).endSpec().build();

		service = client.services().inNamespace(ns).create(service);
		
		
		
		String path = "/";
		
		
		String host = podName + "."+ dns;
		

		
		HashMap<String, String> annotations = new HashMap<>();
		// annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/$1");
		// annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
		annotations.put("kubernetes.io/ingress.class", "nginx");
		annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
		annotations.put("nginx.ingress.kubernetes.io/force-ssl-redirect", "\"true\"");
		annotations.put("nginx.ingress.kubernetes.io/enable-cors", "\"true\"");
		annotations.put("nginx.ingress.kubernetes.io/cors-allow-origin", "'https://0cloud0.com,http://0cloud0.com'");
		
		IngressTLS ingressTLS = new IngressTLSBuilder().withSecretName("0cloud0-wildcard-certs").withHosts(host).build();
		

		IngressBackend backend = new IngressBackendBuilder().withNewServiceName(podName).withNewServicePort(port)
				.build();

		HTTPIngressPath httpIngressPath = new HTTPIngressPathBuilder().withPath(path).withBackend(backend).build();

		HTTPIngressRuleValue httpingressrulebuilder = new HTTPIngressRuleValueBuilder().withPaths(httpIngressPath)
				.build();

		IngressRule ingressrule = new IngressRuleBuilder().withHost(host).withHttp(httpingressrulebuilder).build();

		Ingress ingress = new IngressBuilder().withApiVersion("extensions/v1beta1").withKind("Ingress")
				.withNewMetadata().withName(podName).withAnnotations(annotations).endMetadata()
				.withNewSpec().withRules(ingressrule).withTls(ingressTLS).endSpec().build();

		ingress = client.extensions().ingresses().inNamespace(ns).create(ingress);
		

		client.close();
		
		return host;
		
	
	}
	
	
	public static void getAccessToken(String ns) throws Exception
	{
		KubernetesClient client = K8Deployer.getClient();
		
		String secretName = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		
		System.out.println(secretName);
		
		HashMap<String, String> annotations = new HashMap<>();
		annotations.put("kubernetes.io/service-account.name", "playground");

		HashMap<String, String> labelmap = new HashMap<>();
		labelmap.put(secretName, secretName);
		
		
		ObjectMeta metadata =  new ObjectMetaBuilder()
				.withName(secretName)
				.withLabels(labelmap)
				.withNamespace(ns)
				.withAnnotations(annotations)
				.build();
		
		Secret secret = new SecretBuilder()
				.withApiVersion("v1")
				.withKind("Secret")
				.withMetadata(metadata)
				.withType("kubernetes.io/service-account-token")
				.build();
		
	   client.secrets().inNamespace(ns).create(secret);
	   Thread.sleep(1000);
		
	   SecretList secretlist = client.secrets().inNamespace(ns).withLabel(secretName, secretName).list();
	   
	   List<Secret> listsecret = secretlist.getItems();
	   
	   if(secretlist!=null)
	   {
		   
		for (Iterator iterator = listsecret.iterator(); iterator.hasNext();) {
			Secret secret2 = (Secret) iterator.next();
			   Map<String,String> tokenMap = secret2.getData();
				
				Iterator it = tokenMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
			
		}   
	   
	
	   }
	    
		
	}
	
	
	/**
	
	public static void getAccessToken(String ns) throws Exception
	{
		
		KubernetesClient client = K8Deployer.getClient();
		
		String rolebindName = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		String sa = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		
		ObjectMeta metadata =  new ObjectMetaBuilder()
				.withName(sa)
				.withNamespace(ns)
				.build();
		
		ServiceAccount serviceAccount = new ServiceAccountBuilder()
				.withApiVersion("v1")
				.withMetadata(metadata)
				.build();
		
		client.serviceAccounts().inNamespace(ns).create(serviceAccount);
		
		
	
	    metadata =  new ObjectMetaBuilder()
				.withName(rolebindName)
				.withNamespace(ns)
				.build();

		Subject subject = new SubjectBuilder()
				.withKind("ServiceAccount")
				.withName(sa)
				.build();
	    
		RoleRef roleRef = new RoleRefBuilder()
				.withKind("Role")
				.withName("role-playground-pods")
				.withApiGroup("rbac.authorization.k8s.io")
				.build();
		
		RoleBinding roleBinding = new RoleBindingBuilder()
		.withApiVersion("rbac.authorization.k8s.io/v1")
		.withMetadata(metadata)
		.withSubjects(subject)
		.withRoleRef(roleRef)
		.build();
		
		
		client.rbac().roleBindings().inNamespace(ns).create(roleBinding);
		
		
		serviceAccount = client.serviceAccounts().inNamespace(ns).withName(sa).get();
		
		if(serviceAccount!=null)
		{
			
		
			
			List<ObjectReference> obj_ref = serviceAccount.getSecrets();
			
			for (Iterator iterator = obj_ref.iterator(); iterator.hasNext();) {
				ObjectReference objectReference = (ObjectReference) iterator.next();
				
				System.out.println("objectReference" + objectReference.toString());
				
				Secret secret = client.secrets().inNamespace(ns).withName(objectReference.getName()).get();
				
				Map<String,String> tokenMap = secret.getData();
				
				Iterator it = tokenMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
				
			}
			
		}
		
		client.close();
		
		
	}
	
	**/

}
