/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.p2.repository.its.nxcm1720;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.sonatype.sisu.litmus.testsupport.hamcrest.FileMatchers.exists;

import java.io.File;

import org.junit.Test;
import org.sonatype.nexus.plugins.p2.repository.its.AbstractNexusProxyP2IT;
import org.sonatype.nexus.test.utils.TaskScheduleUtil;

public class NXCM1720UpdateSiteFeatureAndPluginFileNameChangesIT
    extends AbstractNexusProxyP2IT
{

    public NXCM1720UpdateSiteFeatureAndPluginFileNameChangesIT()
    {
        super( "nxcm1720" );
    }

    @Test
    public void test()
        throws Exception
    {
        final File nexusDir = new File( nexusWorkDir, "storage/nxcm1720" );

        TaskScheduleUtil.run( "1" );
        TaskScheduleUtil.waitForAllTasksToStop();

        assertThat(
            new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature_1.0.0-feature.jar" ), not( exists() )
        );
        assertThat(
            new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature.local_1.0.0-feature.jar" ), not( exists() )
        );
        assertThat( new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature_1.0.0.jar" ), exists() );
        assertThat( new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature2_1.0.0.jar" ), exists() );
        assertThat( new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature.local_1.0.0.jar" ), exists() );
        assertThat( new File( nexusDir, "features/com.sonatype.nexus.p2.its.feature2.local_1.0.0.jar" ), exists() );
        assertThat( new File( nexusDir, "plugins/com.sonatype.nexus.p2.its.bundle_1.0.0.jar" ), exists() );
        assertThat( new File( nexusDir, "plugins/com.sonatype.nexus.p2.its.bundle.local_1.0.0.jar" ), exists() );
    }

}
