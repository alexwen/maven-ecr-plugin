package com.mainstreethub.ecr;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.AmazonECRClient;
import com.amazonaws.services.ecr.model.CreateRepositoryRequest;
import com.amazonaws.services.ecr.model.RepositoryAlreadyExistsException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "repository")
public class RepositoryMojo extends AbstractMojo {
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    final AmazonECR ecrClient = new AmazonECRClient();

    final String repositoryName = "mainstreethub/task-api";
    final CreateRepositoryRequest repositoryRequest = new CreateRepositoryRequest()
        .withRepositoryName(repositoryName);

    try {
      getLog().info("Attempting to create ECR repository...");
      ecrClient.createRepository(repositoryRequest);
    } catch (RepositoryAlreadyExistsException ra) {
      getLog().info("Repository already exists: " + repositoryName);
      return;
    } catch (AmazonServiceException ex) {
      throw new MojoExecutionException("Error while retrieving authorization data", ex);
    }

    getLog().info("Repository successfully created: " + repositoryName);
  }
}
