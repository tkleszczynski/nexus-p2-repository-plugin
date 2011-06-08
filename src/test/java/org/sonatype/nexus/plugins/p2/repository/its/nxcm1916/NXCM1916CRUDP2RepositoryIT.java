/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 *
 * All rights reserved. Includes the third-party code listed at http://www.sonatype.com/products/nexus/attributions.
 * Sonatype and Sonatype Nexus are trademarks of Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation.
 * M2Eclipse is a trademark of the Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.p2.repository.its.nxcm1916;

import java.io.IOException;


import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Response;
import org.sonatype.nexus.plugins.p2.repository.its.AbstractNexusProxyP2IntegrationIT;
import org.sonatype.nexus.proxy.maven.RepositoryPolicy;
import org.sonatype.nexus.rest.model.RepositoryResource;
import org.sonatype.nexus.test.utils.RepositoryMessageUtil;


public class NXCM1916CRUDP2RepositoryIT
    extends AbstractNexusProxyP2IntegrationIT
{

    private RepositoryMessageUtil messageUtil;

    public NXCM1916CRUDP2RepositoryIT()
        throws ComponentLookupException
    {
        this.messageUtil =
            new RepositoryMessageUtil( this, this.getJsonXStream(), MediaType.APPLICATION_JSON );
    }

    @Test
    public void createRepositoryTest()
        throws IOException
    {

        RepositoryResource resource = new RepositoryResource();

        resource.setId( "createTestRepo" );
        resource.setRepoType( "hosted" );
        resource.setName( "Create Test Repo" );
        resource.setProvider( "p2" );
        resource.setFormat( "p2" );
        resource.setRepoPolicy( RepositoryPolicy.MIXED.name() );

        this.messageUtil.createRepository( resource );
    }

    @Test
    public void readTest()
        throws IOException
    {

        RepositoryResource resource = new RepositoryResource();

        resource.setId( "readTestRepo" );
        resource.setRepoType( "hosted" );
        resource.setName( "Read Test Repo" );
        resource.setProvider( "p2" );
        resource.setFormat( "p2" );
        resource.setRepoPolicy( RepositoryPolicy.MIXED.name() );

        this.messageUtil.createRepository( resource );

        RepositoryResource responseRepo = (RepositoryResource) this.messageUtil.getRepository( resource.getId() );

        this.messageUtil.validateResourceResponse( resource, responseRepo );

    }

    @Test
    public void updateTest()
        throws IOException
    {

        RepositoryResource resource = new RepositoryResource();

        resource.setId( "updateTestRepo" );
        resource.setRepoType( "hosted" );
        resource.setName( "Update Test Repo" );
        resource.setProvider( "p2" );
        resource.setFormat( "p2" );
        resource.setRepoPolicy( RepositoryPolicy.MIXED.name() );

        resource = (RepositoryResource) this.messageUtil.createRepository( resource );

        resource.setName( "updated repo" );

        this.messageUtil.updateRepo( resource );

    }

    @Test
    public void deleteTest()
        throws IOException
    {
        RepositoryResource resource = new RepositoryResource();

        resource.setId( "deleteTestRepo" );
        resource.setRepoType( "hosted" );
        resource.setName( "Delete Test Repo" );
        resource.setProvider( "p2" );
        resource.setFormat( "p2" );
        resource.setRepoPolicy( RepositoryPolicy.MIXED.name() );

        resource = (RepositoryResource) this.messageUtil.createRepository( resource );

        Response response = this.messageUtil.sendMessage( Method.DELETE, resource );

        if ( !response.getStatus().isSuccess() )
        {
            Assert.fail( "Could not delete Repository: " + response.getStatus() );
        }
        Assert.assertNull( getNexusConfigUtil().getRepo( resource.getId() ) );
    }

}