import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardSidebar } from './dashboard-sidebar.component';

const meta: Meta<DashboardSidebar> = {
  title: 'Components/DashboardSidebar',
  component: DashboardSidebar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardSidebar>;

export const Primary: Story = {
  args: {},
};
